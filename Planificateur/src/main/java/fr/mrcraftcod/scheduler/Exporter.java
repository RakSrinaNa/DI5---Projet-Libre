package fr.mrcraftcod.scheduler;

import fr.mrcraftcod.scheduler.model.Championship;
import fr.mrcraftcod.scheduler.model.GroupStage;
import fr.mrcraftcod.scheduler.model.Match;
import fr.mrcraftcod.scheduler.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

/**
 * Created by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-03-08.
 *
 * @author Thomas Couchoud
 * @since 2019-03-08
 */
public class Exporter{
	private static final Logger LOGGER = LoggerFactory.getLogger(Exporter.class);
	private static DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("ww");
	
	public static void exportChampionship(final Championship championship, final Path folder){
		folder.toFile().mkdirs();
		for(final var gs : championship.getGroupStages()){
			exportGroupStage(gs, folder);
		}
	}
	
	public static void exportGroupStage(final GroupStage groupStage, final Path folder){
		final var file = folder.resolve(groupStage.getName() + ".csv");
		try(final var pw = new PrintWriter(new FileOutputStream(file.toFile()))){
			pw.println(String.format("%s 1,%s 2,%s,%s", StringUtils.getString("team_export_name"), StringUtils.getString("team_export_name"), StringUtils.getString("gymnasium_column_name"), StringUtils.getString("week_export_name")));
			groupStage.getMatches().stream().filter(Match::isAssigned).sorted().forEachOrdered(m -> pw.println(String.format("%s,%s,%s,%s", m.getTeam1().getName(), m.getTeam2().getName(), m.getGymnasium().getName(), StringUtils.getString("week_column_name", weekFormatter.format(m.getDate())))));
		}
		catch(final FileNotFoundException e){
			LOGGER.error("Error creating export file {}", file, e);
		}
	}
}
