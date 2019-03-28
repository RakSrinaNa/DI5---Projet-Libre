package fr.mrcraftcod.scheduler.model;

import fr.mrcraftcod.scheduler.utils.GymnasiumColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
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
class ChampionshipTest{
	private final LocalDate date = LocalDate.now();
	private GroupStage gs1;
	private GroupStage gs2;
	private Match match1112;
	private Match match2122;
	
	@BeforeEach
	void setUp(){
		final var color = new GymnasiumColor();
		final var team11 = new Team(new Gymnasium("g11", "c11", 1, color), "t11", DayOfWeek.MONDAY);
		final var team12 = new Team(new Gymnasium("g12", "c12", 1, color), "t12", DayOfWeek.MONDAY);
		final var team21 = new Team(new Gymnasium("g21", "c21", 2, color), "t21", DayOfWeek.MONDAY);
		final var team22 = new Team(new Gymnasium("g22", "c22", 2, color), "t22", DayOfWeek.MONDAY);
		
		match1112 = new Match(team11, team12, team11.getGymnasium(), date);
		match2122 = new Match(team21, team22, team21.getGymnasium(), date);
		
		gs1 = new GroupStage(null, "gs1");
		gs1.addTeam(team11);
		gs1.addTeam(team12);
		gs1.addMatch(match1112);
		
		gs2 = new GroupStage(null, "gs2");
		gs2.addTeam(team21);
		gs2.addTeam(team22);
		gs2.addMatch(match2122);
	}
	
	@Test
	void addGroupStage(){
		final var c = new Championship(10);
		c.addGroupStage(gs1);
		assertTrue(c.getGroupStages().contains(gs1));
		c.addGroupStage(gs2);
		assertTrue(c.getGroupStages().contains(gs1));
		assertTrue(c.getGroupStages().contains(gs2));
	}
	
	@Test
	void addAllGroupStage(){
		final var c = new Championship(10);
		c.addAllGroupStages(List.of(gs1, gs2));
		assertTrue(c.getGroupStages().contains(gs1));
		assertTrue(c.getGroupStages().contains(gs2));
	}
	
	@Test
	void isGymnasiumFull(){
		final var c = new Championship(10);
		c.addAllGroupStages(List.of(gs1, gs2));
		assertTrue(c.isGymnasiumFull(match1112.getGymnasium(), date));
		assertFalse(c.isGymnasiumFull(match2122.getGymnasium(), date));
	}
	
	@Test
	void addGroupStageDuplicate(){
		final var gs = new Championship(10);
		gs.addGroupStage(gs1);
		assertTrue(gs.getGroupStages().contains(gs1));
		assertFalse(gs.getGroupStages().contains(gs2));
		gs.addGroupStage(gs2);
		assertTrue(gs.getGroupStages().contains(gs1));
		assertTrue(gs.getGroupStages().contains(gs2));
		gs.addGroupStage(gs1);
		gs.addGroupStage(gs2);
		assertEquals(1, gs.getGroupStages().stream().filter(e -> Objects.equals(e, gs1)).count());
		assertEquals(1, gs.getGroupStages().stream().filter(e -> Objects.equals(e, gs2)).count());
	}
	
	@Test
	void addAllGroupStageDuplicate(){
		final var championship = new Championship(10);
		championship.addAllGroupStages(List.of(gs1, gs2));
		assertTrue(championship.getGroupStages().contains(gs1));
		assertTrue(championship.getGroupStages().contains(gs2));
		championship.addAllGroupStages(List.of(gs1, gs2));
		assertEquals(1, championship.getGroupStages().stream().filter(e -> Objects.equals(e, gs1)).count());
		assertEquals(1, championship.getGroupStages().stream().filter(e -> Objects.equals(e, gs2)).count());
		final var groups = new ArrayList<GroupStage>();
		groups.add(gs1);
		groups.add(gs2);
		groups.add(null);
		championship.addAllGroupStages(groups);
		assertEquals(1, championship.getGroupStages().stream().filter(e -> Objects.equals(e, gs1)).count());
		assertEquals(1, championship.getGroupStages().stream().filter(e -> Objects.equals(e, gs2)).count());
	}
	
	@Test
	void addWrongGroupStage(){
		final var championship = new Championship(10);
		final Executable executable1 = () -> championship.addGroupStage(null);
		assertThrows(IllegalArgumentException.class, executable1);
	}
}