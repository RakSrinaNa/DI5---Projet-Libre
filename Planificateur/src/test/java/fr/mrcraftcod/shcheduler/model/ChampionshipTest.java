package fr.mrcraftcod.shcheduler.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-22.
 *
 * @author Thomas Couchoud
 * @since 2019-01-22
 */
class ChampionshipTest{
	private LocalDate date = LocalDate.now();
	private GroupStage gs1;
	private GroupStage gs2;
	private Match match1112;
	private Match match2122;
	
	@BeforeEach
	void setUp(){
		final var team11 = new Team(new Gymnasium("g11", "c11", 1), "t11");
		final var team12 = new Team(new Gymnasium("g12", "c12", 1), "t12");
		final var team21 = new Team(new Gymnasium("g21", "c21", 2), "t21");
		final var team22 = new Team(new Gymnasium("g22", "c22", 2), "t22");
		
		match1112 = new Match(team11, team12, team11.getGymnasium(), date);
		match2122 = new Match(team21, team22, team21.getGymnasium(), date);
		
		gs1 = new GroupStage("gs1");
		gs1.addTeam(team11);
		gs1.addTeam(team12);
		gs1.addMatch(match1112);
		
		gs2 = new GroupStage("gs2");
		gs2.addTeam(team21);
		gs2.addTeam(team22);
		gs2.addMatch(match2122);
	}
	
	@Test
	void addGroupStage(){
		final var c = new Championship();
		c.addGroupStage(gs1);
		assertTrue(c.getGroupStages().contains(gs1));
		c.addGroupStage(gs2);
		assertTrue(c.getGroupStages().contains(gs1));
		assertTrue(c.getGroupStages().contains(gs2));
	}
	
	@Test
	void addAllGroupStage(){
		final var c = new Championship();
		c.addAllGroupStages(List.of(gs1, gs2));
		assertTrue(c.getGroupStages().contains(gs1));
		assertTrue(c.getGroupStages().contains(gs2));
	}
	
	@Test
	void isGymnasiumFull(){
		final var c = new Championship();
		c.addAllGroupStages(List.of(gs1, gs2));
		assertTrue(c.isGymnasiumFull(match1112.getGymnasium(), date));
		assertFalse(c.isGymnasiumFull(match2122.getGymnasium(), date));
	}
}