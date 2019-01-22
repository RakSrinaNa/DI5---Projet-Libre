package fr.mrcraftcod.shcheduler;

import fr.mrcraftcod.shcheduler.model.Gymnasium;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-22.
 *
 * @author Thomas Couchoud
 * @since 2019-01-22
 */
class ParserTest{
	private Parser parser;
	
	@BeforeEach
	void setUp(){
		parser = new Parser(',');
	}
	
	@Test
	void parse(){
	}
	
	@Test
	void getGymsValid1() throws IOException{
		final var g1 = new Gymnasium("G1", "C1", 1);
		final var g2 = new Gymnasium("G2", "C2", 2);
		final var g3 = new Gymnasium("G3", "C3", 3);
		final var g4 = new Gymnasium("G4", "C4", 4);
		
		final var gyms = parser.getGymnasiums(Files.readAllLines(Path.of(Parser.class.getResource("/gymnasiums/valid1.csv").getPath())));
		assertEquals(4, gyms.size());
		assertTrue(gyms.contains(g1));
		assertTrue(gyms.contains(g2));
		assertTrue(gyms.contains(g3));
		assertTrue(gyms.contains(g4));
	}
	
	@Test
	void getGymsDuplicates1() throws IOException{
		final var g1 = new Gymnasium("G1", "C1", 1);
		final var g2 = new Gymnasium("G2", "C2", 2);
		final var g3 = new Gymnasium("G3", "C3", 3);
		final var g4 = new Gymnasium("G4", "C4", 4);
		
		final var gyms = parser.getGymnasiums(Files.readAllLines(Path.of(Parser.class.getResource("/gymnasiums/duplicates1.csv").getPath())));
		assertEquals(4, gyms.size());
		assertTrue(gyms.contains(g1));
		assertTrue(gyms.contains(g2));
		assertTrue(gyms.contains(g3));
		assertTrue(gyms.contains(g4));
	}
	
	@Test
	void getGroupStages(){
	}
	
	@Test
	void buildMatches(){
	}
}