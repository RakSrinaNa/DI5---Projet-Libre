package fr.mrcraftcod.shcheduler;

import fr.mrcraftcod.shcheduler.model.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class Parser{
	private final char csvSeparator;
	
	public Parser(final char csvSeparator){
		this.csvSeparator = csvSeparator;
	}
	
	public Championship parse(final Path gymnasiumsCsvFile, final Path teamsCsvFile) throws IOException{
		return parse(new FileInputStream(gymnasiumsCsvFile.toFile()), new FileInputStream(teamsCsvFile.toFile()));
	}
	
	public Championship parse(final InputStream gymnasiumsCsvFile, final InputStream teamsCsvFile) throws IOException{
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
	
	public Collection<Gymnasium> getGymnasiums(Collection<String> gymnasiumLines){
		final var gyms = new ArrayList<Gymnasium>();
		for(String gym : gymnasiumLines){
			String[] elements = gym.split(";|,");
			gyms.add(new Gymnasium(elements[0], elements[2], Integer.parseInt(elements[1])));
		}
		return gyms;
	}
	
	public Collection<GroupStage> getGroupStages(Collection<Gymnasium> gymnasiums, Collection<String> teamLines){
		final var groups = new ArrayList<GroupStage>();
		for(String team : teamLines){
			String[] elements = team.split(";");
			
			groups.add(new GroupStage(elements[3]));
			GroupStage group = groups.stream().filter(g -> g.getName().equals(elements[3])).findFirst().get();
			
			Gymnasium gym = gymnasiums.stream().filter(g -> g.getName().equals(elements[1])).findFirst().get();
			
			group.addTeam(new Team(gym, elements[0]));
		}
		return groups;
	}
	
	public void buildMatches(Collection<GroupStage> groupStages){
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
