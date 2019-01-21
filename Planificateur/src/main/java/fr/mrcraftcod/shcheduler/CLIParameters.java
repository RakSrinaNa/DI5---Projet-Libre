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
			"-c",
			"--config"
	}, description = "Path to the csv configuration", converter = FileConverter.class, required = true)
	private File csvConfigFile;
	
	public File getCsvConfigFile(){
		return csvConfigFile;
	}
}
