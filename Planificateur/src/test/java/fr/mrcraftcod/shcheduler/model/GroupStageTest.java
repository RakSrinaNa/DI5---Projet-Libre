package fr.mrcraftcod.shcheduler.model;

import fr.mrcraftcod.shcheduler.utils.GymnasiumColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-22.
 *
 * @author Thomas Couchoud
 * @since 2019-01-22
 */
class GroupStageTest{
	private Team team1;
	private Team team2;
	private LocalDate date;
	private Match match1;
	private Match match2;
	private GymnasiumColor color = new GymnasiumColor();
	
	@BeforeEach
	void setUp(){
		date = LocalDate.now();
		team1 = new Team(new Gymnasium("gName1", "gCity1", Integer.MAX_VALUE, color), "tName1", DayOfWeek.MONDAY);
		team2 = new Team(new Gymnasium("gName2", "gCity2", Integer.MAX_VALUE, color), "tName2", DayOfWeek.MONDAY);
		match1 = new Match(team1, team2, team1.getGymnasium(), date);
		match2 = new Match(team2, team1, team2.getGymnasium(), date);
	}
	
	@Test
	void addMatch(){
		final var gs = new GroupStage(null, "gs");
		gs.addAllTeams(List.of(team1, team2));
		gs.addMatch(match1);
		assertTrue(gs.getMatches().contains(match1));
		assertFalse(gs.getMatches().contains(match2));
		gs.addMatch(match2);
		assertTrue(gs.getMatches().contains(match1));
		assertTrue(gs.getMatches().contains(match2));
		gs.addMatch(match1);
		gs.addMatch(match2);
		assertEquals(1, gs.getMatches().stream().filter(e -> Objects.equals(e, match1)).count());
		assertEquals(1, gs.getMatches().stream().filter(e -> Objects.equals(e, match2)).count());
	}
	
	@Test
	void addAllMatches(){
		final var gs = new GroupStage(null, "gs");
		gs.addAllTeams(List.of(team1, team2));
		assertTrue(gs.addAllMatches(List.of(match1, match2)));
		assertTrue(gs.getMatches().contains(match1));
		assertTrue(gs.getMatches().contains(match2));
		assertTrue(gs.addAllMatches(List.of(match1, match2)));
		assertEquals(1, gs.getMatches().stream().filter(e -> Objects.equals(e, match1)).count());
		assertEquals(1, gs.getMatches().stream().filter(e -> Objects.equals(e, match2)).count());
		final var matches = new ArrayList<Match>();
		matches.add(match1);
		matches.add(match2);
		matches.add(null);
		assertTrue(gs.addAllMatches(matches));
		assertEquals(1, gs.getMatches().stream().filter(e -> Objects.equals(e, match1)).count());
		assertEquals(1, gs.getMatches().stream().filter(e -> Objects.equals(e, match2)).count());
	}
	
	@Test
	void addTeam(){
		final var gs = new GroupStage(null, "gs");
		gs.addTeam(team1);
		assertTrue(gs.getTeams().contains(team1));
		assertFalse(gs.getTeams().contains(team2));
		gs.addTeam(team2);
		assertTrue(gs.getTeams().contains(team1));
		assertTrue(gs.getTeams().contains(team2));
		gs.addTeam(team1);
		gs.addTeam(team2);
		assertEquals(1, gs.getTeams().stream().filter(e -> Objects.equals(e, team1)).count());
		assertEquals(1, gs.getTeams().stream().filter(e -> Objects.equals(e, team2)).count());
	}
	
	@Test
	void addAllTeams(){
		final var gs = new GroupStage(null, "gs");
		gs.addAllTeams(List.of(team1, team2));
		assertTrue(gs.getTeams().contains(team1));
		assertTrue(gs.getTeams().contains(team2));
		gs.addAllTeams(List.of(team1, team2));
		assertEquals(1, gs.getTeams().stream().filter(e -> Objects.equals(e, team1)).count());
		assertEquals(1, gs.getTeams().stream().filter(e -> Objects.equals(e, team2)).count());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"gs",
			"name",
			"robert1"
	})
	void getName(final String name){
		final var gs = new GroupStage(null, name);
		assertEquals(name, gs.getName());
	}
	
	@Test
	void equals(){
		final var gs1 = new GroupStage(null, "gs1");
		final var gs2 = new GroupStage(null, "gs1");
		final var gs3 = new GroupStage(null, "gs2");
		assertEquals(gs1, gs2);
		assertNotEquals(gs1, gs3);
	}
	
	@Test
	void addWrongMatch(){
		final var gs = new GroupStage(null, "gs");
		gs.addAllTeams(List.of(team1, team2));
		final var wMatch1 = new Match(team1, new Team(new Gymnasium("gNameA", "gCityA", 2, color), "tNameA", DayOfWeek.MONDAY), team1.getGymnasium(), date);
		gs.addAllTeams(List.of(team1, team2));
		final Executable executable1 = () -> gs.addMatch(null);
		assertThrows(IllegalArgumentException.class, executable1);
		final Executable executable2 = () -> gs.addMatch(wMatch1);
		assertThrows(IllegalArgumentException.class, executable2);
		assertFalse(gs.addAllMatches(List.of(match1, wMatch1)));
		assertTrue(gs.getMatches().contains(match1));
		assertFalse(gs.getMatches().contains(wMatch1));
	}
}