package fr.mrcraftcod.shcheduler;

import fr.mrcraftcod.shcheduler.model.Championship;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class Parser{
	public static Championship parse(final File gymnasiumsCsvFile, final File teamsCsvFile) throws IOException {
		Files.readAllLines(gymnasiumsCsvFile.toPath());
		return null;
	}
}
