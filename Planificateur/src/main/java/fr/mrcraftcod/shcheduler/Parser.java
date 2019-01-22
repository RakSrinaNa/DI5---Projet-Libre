package fr.mrcraftcod.shcheduler;

import fr.mrcraftcod.shcheduler.model.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class Parser{
	public static Championship parse(final File gymnasiumsCsvFile, final File teamsCsvFile) throws IOException{
		List<String> gymnasiumLines = Files.readAllLines(gymnasiumsCsvFile.toPath());
		List<String> teamLines = Files.readAllLines(teamsCsvFile.toPath());
		
		Set<Gymnasium> gymnasiums = new HashSet<>();
		Set<GroupStage> groupStages = new HashSet<>();
		
		gymnasiums.addAll(getGyms(gymnasiumLines));
		groupStages.addAll(getGroupStages(gymnasiums, teamLines));
		buildMatches(groupStages);
		
		Championship championship = new Championship();
		championship.addAllGroupStages(groupStages);
		
		return championship;
	}
	
	public static Collection<Gymnasium> getGyms(List<String> gymnasiumLines){
		final var gyms = new ArrayList<Gymnasium>();
		for(String gym : gymnasiumLines){
			String[] elements = gym.split(";");
			gyms.add(new Gymnasium(elements[0], elements[2], Integer.parseInt(elements[1])));
		}
		return gyms;
	}
	
	public static Collection<GroupStage> getGroupStages(Collection<Gymnasium> gymnasiums, List<String> teamLines){
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
	
	public static void buildMatches(Collection<GroupStage> groupStages){
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
