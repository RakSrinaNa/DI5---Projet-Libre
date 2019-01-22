package fr.mrcraftcod.shcheduler;

import fr.mrcraftcod.shcheduler.exceptions.IllegalCSVFormatException;
import fr.mrcraftcod.shcheduler.exceptions.ParserException;
import fr.mrcraftcod.shcheduler.model.Gymnasium;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-22.
 *
 * @author Thomas Couchoud
 * @since 2019-01-22
 */
class ParserGymnasiumsTest{
	private Parser parser;
	
	@BeforeEach
	void setUp(){
		parser = new Parser(',');
	}
	
	@Test
	void getGymsValid1(){
		final var g1 = new Gymnasium("G1", "C1", 1);
		final var g2 = new Gymnasium("G2", "C2", 2);
		final var g3 = new Gymnasium("G3", "C3", 3);
		final var g4 = new Gymnasium("G4", "C4", 4);
		
		final var gyms = parser.getGymnasiums(getLines(Parser.class.getResourceAsStream("/gymnasiums/valid1.csv")));
		assertEquals(4, gyms.size());
		assertTrue(gyms.contains(g1));
		assertTrue(gyms.contains(g2));
		assertTrue(gyms.contains(g3));
		assertTrue(gyms.contains(g4));
	}
	
	private Collection<String> getLines(final InputStream is){
		return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
	}
	
	@Test
	void getGymsDuplicates1(){
		final var g1 = new Gymnasium("G1", "C1", 1);
		final var g2 = new Gymnasium("G2", "C2", 2);
		final var g3 = new Gymnasium("G3", "C3", 3);
		final var g4 = new Gymnasium("G4", "C4", 4);
		
		final var gyms = parser.getGymnasiums(getLines(Parser.class.getResourceAsStream("/gymnasiums/duplicates1.csv")));
		assertEquals(4, gyms.size());
		assertTrue(gyms.contains(g1));
		assertTrue(gyms.contains(g2));
		assertTrue(gyms.contains(g3));
		assertTrue(gyms.contains(g4));
	}
	
	@Test
	void getGymsBigSize1(){
		final Executable executable1 = () -> parser.getGymnasiums(getLines(Parser.class.getResourceAsStream("/gymnasiums/bigSize1.csv")));
		try{
			executable1.execute();
		}
		catch(final Throwable e){
			if(!(e instanceof ParserException)){
				fail("Wrong exception thrown");
			}
			assertEquals(NumberFormatException.class, e.getCause().getClass());
		}
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"/gymnasiums/invalidSize1.csv"
	})
	void getGymsInvalidSizes(final String path){
		final Executable executable1 = () -> parser.getGymnasiums(getLines(Parser.class.getResourceAsStream(path)));
		try{
			executable1.execute();
		}
		catch(final Throwable e){
			if(!(e instanceof ParserException)){
				fail("Wrong exception thrown");
			}
			assertEquals(IllegalArgumentException.class, e.getCause().getClass());
		}
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"/gymnasiums/invalidSize2.csv",
			"/gymnasiums/invalidSize3.csv"
	})
	void getGymsInvalidSizes2(final String path){
		final Executable executable1 = () -> parser.getGymnasiums(getLines(Parser.class.getResourceAsStream(path)));
		try{
			executable1.execute();
		}
		catch(final Throwable e){
			if(!(e instanceof ParserException)){
				fail("Wrong exception thrown");
			}
			assertEquals(NumberFormatException.class, e.getCause().getClass());
		}
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"/gymnasiums/invalidCity1.csv",
			"/gymnasiums/invalidCity2.csv"
	})
	void getGymsInvalidCities(final String path){
		final Executable executable1 = () -> parser.getGymnasiums(getLines(Parser.class.getResourceAsStream(path)));
		try{
			executable1.execute();
		}
		catch(final Throwable e){
			if(!(e instanceof ParserException)){
				fail("Wrong exception thrown");
			}
			assertEquals(IllegalArgumentException.class, e.getCause().getClass());
		}
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"/gymnasiums/invalidName1.csv",
			"/gymnasiums/invalidName2.csv"
	})
	void getGymsInvalidNames(final String path){
		final Executable executable1 = () -> parser.getGymnasiums(getLines(Parser.class.getResourceAsStream(path)));
		try{
			executable1.execute();
		}
		catch(final Throwable e){
			if(!(e instanceof ParserException)){
				fail("Wrong exception thrown");
			}
			assertEquals(IllegalArgumentException.class, e.getCause().getClass());
		}
	}
	
	@Test
	void getGymsEmpty(){
		final var gyms = parser.getGymnasiums(List.of());
		assertEquals(0, gyms.size());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"/gymnasiums/invalid1.csv"
	})
	void getGymsInvalid(final String path){
		final Executable executable1 = () -> parser.getGymnasiums(getLines(Parser.class.getResourceAsStream(path)));
		try{
			executable1.execute();
		}
		catch(final Throwable e){
			if(!(e instanceof ParserException)){
				fail("Wrong exception thrown");
			}
			assertEquals(IllegalCSVFormatException.class, e.getCause().getClass());
		}
	}
}