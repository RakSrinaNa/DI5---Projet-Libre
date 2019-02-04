package fr.mrcraftcod.shcheduler;

import fr.mrcraftcod.shcheduler.exceptions.IllegalCSVFormatException;
import fr.mrcraftcod.shcheduler.exceptions.ParserException;
import fr.mrcraftcod.shcheduler.model.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Parser for the CSV config files.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class Parser{
	private final String csvSeparator;
	
	/**
	 * Constructor.
	 *
	 * @param csvSeparator The separator used in the CSV files.
	 */
	public Parser(final char csvSeparator){
		this.csvSeparator = "" + csvSeparator;
	}
	
	/**
	 * Parse the config.
	 *
	 * @param gymnasiumsCsvFile The config of the gymnasiums.
	 * @param teamsCsvFile      The config of the teams.
	 *
	 * @return The championship parsed.
	 *
	 * @throws IOException     If the file couldn't be read.
	 * @throws ParserException If the parser encountered an error.
	 */
	public Championship parse(final Path gymnasiumsCsvFile, final Path teamsCsvFile) throws IOException, ParserException{
		return parse(new FileInputStream(gymnasiumsCsvFile.toFile()), new FileInputStream(teamsCsvFile.toFile()));
	}
	
	/**
	 * Parse the config.
	 *
	 * @param gymnasiumsCsvFile The config of the gymnasiums.
	 * @param teamsCsvFile      The config of the teams.
	 *
	 * @return The championship parsed.
	 *
	 * @throws ParserException If the parser encountered an error.
	 */
	public Championship parse(final InputStream gymnasiumsCsvFile, final InputStream teamsCsvFile) throws ParserException{
		List<String> gymnasiumLines = new BufferedReader(new InputStreamReader(gymnasiumsCsvFile, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
		List<String> teamLines = new BufferedReader(new InputStreamReader(teamsCsvFile, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
		
		Set<Gymnasium> gymnasiums = new HashSet<>();
		Set<GroupStage> groupStages = new HashSet<>();
		
		gymnasiums.addAll(getGymnasiums(gymnasiumLines));
		groupStages.addAll(getGroupStages(gymnasiums, teamLines));
		buildMatches(groupStages);
		
		Championship championship = new Championship();
		championship.addAllGroupStages(groupStages);
		
		return championship;
	}
	
	/**
	 * Par se the gymnasiums.
	 *
	 * @param gymnasiumLines The CSV lines of the gymnasiums.
	 *
	 * @return The gymnasiums.
	 *
	 * @throws ParserException If the parser encountered an error.
	 */
	public Collection<Gymnasium> getGymnasiums(final Collection<String> gymnasiumLines) throws ParserException{
		final List<String> colors = Arrays.asList("blue", "green", "red", "violet", "yellow", "rgb(255,125,75)");
		final var gyms = new ArrayList<Gymnasium>();
		int i = 0;
		for(String gym : gymnasiumLines){
			String[] elements = gym.split(csvSeparator, -1);
			
			if(elements.length != 3){
				throw new ParserException("Wrong format", new IllegalCSVFormatException("No 3 elements"));
			}
			
			int cap;
			try{
				cap = Integer.parseInt(elements[1]);
			}
			catch(NumberFormatException e){
				throw new ParserException("Invalid gymnasium capacity", e);
			}
			String name = elements[0];
			String city = elements[2];
			
			try{
				Gymnasium g = new Gymnasium(name, city, cap, colors.get(i++ % colors.size()));
				if(!gyms.contains(g)){
					gyms.add(g);
				}
			}
			catch(IllegalArgumentException e){
				throw new ParserException("Gymnasium parsing error", e);
			}
		}
		return gyms;
	}
	
	/**
	 * Par se the group stages and teams.
	 *
	 * @param gymnasiums The gymnasiums.
	 * @param teamLines  The CSV lines of the teams/group stages.
	 *
	 * @return The group stages.
	 *
	 * @throws ParserException If the parser encountered an error.
	 */
	public Collection<GroupStage> getGroupStages(final Collection<Gymnasium> gymnasiums, final Collection<String> teamLines) throws ParserException{
		final var groups = new ArrayList<GroupStage>();
		for(String team : teamLines){
			String[] elements = team.split(csvSeparator, -1);
			
			if(elements.length != 4){
				throw new ParserException("Wrong format", new IllegalCSVFormatException("No 3 elements"));
			}
			
			try{
				final var group = new GroupStage(elements[3]);
				if(!groups.contains(group)){
					groups.add(group);
				}
			}
			catch(IllegalArgumentException e){
				throw new ParserException("Team parsing error", e);
			}
			GroupStage group = groups.stream().filter(g -> g.getName().equals(elements[3])).findFirst().get();
			
			Gymnasium gym = gymnasiums.stream().filter(g -> g.getName().equals(elements[1])).findFirst().get();
			
			try{
				Team t = new Team(gym, elements[0], DayOfWeek.MONDAY);
				if(!groups.contains(t)){
					group.addTeam(t);
				}
			}
			catch(IllegalArgumentException e){
				throw new ParserException("Team parsing error", e);
			}
		}
		return groups;
	}
	
	/**
	 * Build the matches between all the teams in each group stage.
	 *
	 * @param groupStages The group stages.
	 */
	public void buildMatches(final Collection<GroupStage> groupStages){
		for(GroupStage group : groupStages){
			for(Team team1 : group.getTeams()){
				for(Team team2 : group.getTeams()){
					if(!team1.equals(team2)){
						group.addMatch(new Match(team1, team2, null, null));
					}
				}
			}
		}
	}
}
