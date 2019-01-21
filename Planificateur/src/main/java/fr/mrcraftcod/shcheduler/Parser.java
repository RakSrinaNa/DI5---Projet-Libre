package fr.mrcraftcod.shcheduler;

import fr.mrcraftcod.shcheduler.model.Championship;
import fr.mrcraftcod.shcheduler.model.Gymnasium;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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

		List<Gymnasium> gymnasiums = new ArrayList<>();
		List<Gymnasium> teams = new ArrayList<>();

		for(String gym : gymnasiumLine){
			String[] elements = gym.split(";");
		}

		return null;
	}
}
