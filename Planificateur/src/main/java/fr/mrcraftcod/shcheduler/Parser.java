package fr.mrcraftcod.shcheduler;

import fr.mrcraftcod.shcheduler.exceptions.IllegalCSVFormatException;
import fr.mrcraftcod.shcheduler.exceptions.ParserException;
import fr.mrcraftcod.shcheduler.model.*;
import fr.mrcraftcod.shcheduler.utils.GymnasiumColor;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
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
	private final List<GymnasiumColor> colors = new ArrayList<>(){{
		add(new GymnasiumColor());
	}};
	
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
	@SuppressWarnings("WeakerAccess")
	public Championship parse(final InputStream gymnasiumsCsvFile, final InputStream teamsCsvFile) throws ParserException{
		final var gymnasiumLines = new BufferedReader(new InputStreamReader(gymnasiumsCsvFile, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
		final var teamLines = new BufferedReader(new InputStreamReader(teamsCsvFile, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
		
		final var championship = new Championship();
		
		final var gymnasiums = new HashSet<>(getGymnasiums(gymnasiumLines));
		final var groupStages = new HashSet<>(getGroupStages(championship, gymnasiums, teamLines));
		buildMatches(groupStages);
		championship.addAllGroupStages(groupStages);
		
		final var numberOfWeeks = 10;
		final var initialDate = LocalDate.now().minusDays(Utils.getDaysToRemove(LocalDate.now().getDayOfWeek()));
		for(var i = 0; i < numberOfWeeks; i++){
			championship.addDate(initialDate.plusDays(i * 7));
		}
		
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
	Collection<Gymnasium> getGymnasiums(final Collection<String> gymnasiumLines) throws ParserException{
		final var gyms = new ArrayList<Gymnasium>();
		var colorIndex = 0;
		for(final var gymnasiumLine : gymnasiumLines){
			if(gymnasiumLine.isBlank()){
				continue;
			}
			final var elements = gymnasiumLine.split(csvSeparator, -1);
			
			if(elements.length != 3){
				throw new ParserException("Wrong format", new IllegalCSVFormatException("No 3 elements"));
			}
			
			final int capacity;
			try{
				capacity = Integer.parseInt(elements[1]);
			}
			catch(final NumberFormatException e){
				throw new ParserException("Invalid gymnasium capacity", e);
			}
			if(capacity < 0){
				throw new ParserException("Capacity must be positive", new IllegalArgumentException());
			}
			final var name = elements[0];
			final var city = elements[2];
			
			try{
				final var gymnasium = new Gymnasium(name, city, capacity, colors.get(colorIndex++ % colors.size()));
				if(!gyms.contains(gymnasium)){
					gyms.add(gymnasium);
				}
			}
			catch(final IllegalArgumentException e){
				throw new ParserException("Gymnasium parsing error", e);
			}
		}
		return gyms;
	}
	
	/**
	 * Parse the group stages and teams.
	 *
	 * @param championship The championship.
	 * @param gymnasiums   The gymnasiums.
	 * @param teamLines    The CSV lines of the teams/group stages.
	 *
	 * @return The group stages.
	 *
	 * @throws ParserException If the parser encountered an error.
	 */
	private Collection<GroupStage> getGroupStages(final Championship championship, final Collection<Gymnasium> gymnasiums, final Collection<String> teamLines) throws ParserException{
		final var groups = new ArrayList<GroupStage>();
		for(final var teamLine : teamLines){
			if(teamLine.isBlank()){
				continue;
			}
			final var elements = teamLine.split(csvSeparator, -1);
			
			if(elements.length != 4){
				throw new ParserException("Wrong format", new IllegalCSVFormatException("No 3 elements"));
			}
			
			try{
				final var group = new GroupStage(championship, elements[3]);
				if(!groups.contains(group)){
					groups.add(group);
				}
			}
			catch(final IllegalArgumentException e){
				throw new ParserException("Team parsing error", e);
			}
			final var group = groups.stream().filter(g -> g.getName().equals(elements[3])).findFirst().orElseThrow(() -> new ParserException("Group stage " + elements[3] + " not found", new IllegalStateException()));
			final var gymnasium = gymnasiums.stream().filter(g -> g.getName().equals(elements[1])).findFirst().orElseThrow(() -> new ParserException("Gymnasium " + elements[1] + " not found", new IllegalStateException()));
			
			try{
				final var team = new Team(gymnasium, elements[0], DayOfWeek.MONDAY);
				if(!group.containsTeam(team)){
					group.addTeam(team);
				}
			}
			catch(final IllegalArgumentException e){
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
	private void buildMatches(final Collection<GroupStage> groupStages){
		for(final var group : groupStages){
			for(final var team1 : group.getTeams()){
				for(final var team2 : group.getTeams()){
					if(!team1.equals(team2)){
						group.addMatch(new Match(team1, team2, null, null));
					}
				}
			}
		}
	}
}
