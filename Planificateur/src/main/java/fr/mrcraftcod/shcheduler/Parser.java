package fr.mrcraftcod.shcheduler;

import fr.mrcraftcod.shcheduler.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class Parser{
	public static Championship parse(final File gymnasiumsCsvFile, final File teamsCsvFile) throws IOException {
		List<String> gymnasiumLine = Files.readAllLines(gymnasiumsCsvFile.toPath());
		List<String> teamLine = Files.readAllLines(teamsCsvFile.toPath());

		Set<Gymnasium> gymnasiums = new HashSet<>();
		Set<GroupStage> groupStages = new HashSet<>();

		for(String gym : gymnasiumLine){
			String[] elements = gym.split(";");
			gymnasiums.add(new Gymnasium(elements[0], elements[2], Integer.parseInt(elements[1])));
		}

		for(String team : teamLine){
			String[] elements = team.split(";");

			groupStages.add(new GroupStage(elements[3]));
			GroupStage group = groupStages.stream().filter(g -> g.getName().equals(elements[3])).findFirst().get();

			Gymnasium gym = gymnasiums.stream().filter(g -> g.getName().equals(elements[1])).findFirst().get();

			group.addTeam(new Team(gym, elements[0]));
		}

		for(GroupStage group : groupStages)
			for(Team team1 : group.getTeams())
				for(Team team2 : group.getTeams())
					if(!team1.equals(team2))
						group.addMatch(new Match(team1, team2, null, null));

		Championship championship = new Championship();
		championship.addAllGroupStages(groupStages);

		return championship;
	}
}
