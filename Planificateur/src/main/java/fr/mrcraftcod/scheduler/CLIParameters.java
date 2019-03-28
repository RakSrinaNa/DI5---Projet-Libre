package fr.mrcraftcod.scheduler;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.PathConverter;
import java.nio.file.Path;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 01/09/2018.
 *
 * @author Thomas Couchoud
 * @since 2018-09-01
 */
public class CLIParameters{
	@Parameter(names = {
			"-g",
			"--gymnasium"
	}, description = "Path to the csv gymnasium configuration", converter = PathConverter.class, required = true)
	private Path csvGymnasiumConfigFile;
	
	@Parameter(names = {
			"-t",
			"--team"
	}, description = "Path to the csv team configuration", converter = PathConverter.class, required = true)
	private Path csvTeamConfigFile;
	
	@Parameter(names = {
			"-w",
			"--weeks"
	}, description = "The number of weeks of the championship")
	private int championshipWeeks = 10;
	
	public int getChampionshipWeeks(){
		return this.championshipWeeks;
	}
	
	/**
	 * Get the path to the CSV file for the gymnasiums.
	 *
	 * @return The path.
	 */
	public Path getCsvGymnasiumConfigFile(){
		return this.csvGymnasiumConfigFile;
	}
	
	/**
	 * Get the path to the CSV file for the teams.
	 *
	 * @return The path.
	 */
	public Path getCsvTeamConfigFile(){
		return this.csvTeamConfigFile;
	}
}
