package fr.mrcraftcod.shcheduler;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import java.io.File;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 01/09/2018.
 *
 * @author Thomas Couchoud
 * @since 2018-09-01
 */
@SuppressWarnings("unused")
public class CLIParameters{
	@Parameter(names = {
			"-g",
			"--gymnasium"
	}, description = "Path to the csv gymnasium configuration", converter = FileConverter.class, required = true)
	private File csvGymnasiumConfigFile;
	
	@Parameter(names = {
			"-t",
			"--team"
	}, description = "Path to the csv team configuration", converter = FileConverter.class, required = true)
	private File csvTeamConfigFile;
	
	public File getCsvGymnasiumConfigFile(){
		return csvGymnasiumConfigFile;
	}
	
	public File getCsvTeamConfigFile(){
		return csvTeamConfigFile;
	}
}
