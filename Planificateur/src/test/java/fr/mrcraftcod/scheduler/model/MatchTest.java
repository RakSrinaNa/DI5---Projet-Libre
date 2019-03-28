package fr.mrcraftcod.scheduler.model;

import fr.mrcraftcod.scheduler.utils.GymnasiumColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-22.
 *
 * @author Thomas Couchoud
 * @since 2019-01-22
 */
class MatchTest{
	private Team team1;
	private Team team2;
	private LocalDate date;
	private final GymnasiumColor color = new GymnasiumColor();
	
	@BeforeEach
	void setUp(){
		team1 = new Team(new Gymnasium("gym1", "city1", Integer.MAX_VALUE, color), "team1", DayOfWeek.MONDAY);
		team2 = new Team(new Gymnasium("gym2", "city2", Integer.MAX_VALUE, color), "team2", DayOfWeek.MONDAY);
		date = LocalDate.now();
	}
	
	@Test
	void getCity(){
		final var match = new Match(team1, team2, team1.getGymnasium(), date);
		assertEquals(team1.getCity(), match.getCity());
	}
	
	@Test
	void getDate(){
		final var match = new Match(team1, team2, team1.getGymnasium(), date);
		assertEquals(date, match.getDate());
	}
	
	@Test
	void setDate(){
		final var match = new Match(team1, team2, team1.getGymnasium(), date);
		final var date2 = LocalDate.now().minusDays(1);
		match.setDate(date2);
		assertEquals(date2, match.getDate());
	}
	
	@Test
	void getGymnasium(){
		final var match = new Match(team1, team2, team1.getGymnasium(), date);
		assertEquals(team1.getGymnasium(), match.getGymnasium());
	}
	
	@Test
	void setGymnasium(){
		final var match = new Match(team1, team2, team1.getGymnasium(), date);
		match.setGymnasium(team2.getGymnasium());
		assertEquals(team2.getGymnasium(), match.getGymnasium());
		match.setGymnasium(null);
		assertNull(match.getGymnasium());
	}
	
	@Test
	void setInvalidGymnasium(){
		final var match = new Match(team1, team2, team1.getGymnasium(), date);
		final Executable executable1 = () -> match.setGymnasium(new Gymnasium("gNameA", "gCityA", Integer.MAX_VALUE, color));
		assertThrows(IllegalArgumentException.class, executable1);
	}
	
	@Test
	void getTeam1(){
		final var match = new Match(team1, team2, team1.getGymnasium(), date);
		assertEquals(team1, match.getTeam1());
	}
	
	@Test
	void getTeam2(){
		final var match = new Match(team1, team2, team1.getGymnasium(), date);
		assertEquals(team2, match.getTeam2());
	}
	
	@Test
	void equals(){
		final var match1 = new Match(team1, team2, team1.getGymnasium(), date);
		final var match2 = new Match(team1, team2, team1.getGymnasium(), date);
		assertEquals(match1, match2);
		final var match3 = new Match(team2, team1, team1.getGymnasium(), date);
		assertNotEquals(match1, match3);
	}
	
	@Test
	void construct(){
		new Match(team1, team2, team1.getGymnasium(), date);
		new Match(team1, team2, null, date);
		new Match(team1, team2, team1.getGymnasium(), null);
		new Match(team1, team2, null, null);
	}
	
	@Test
	void failConstruct(){
		final Executable executable1 = () -> new Match(team1, team2, new Gymnasium("gNameA", "cNameA", Integer.MAX_VALUE, color), date);
		assertThrows(IllegalArgumentException.class, executable1);
		final Executable executable2 = () -> new Match(null, team2, team2.getGymnasium(), date);
		assertThrows(IllegalArgumentException.class, executable2);
		final Executable executable3 = () -> new Match(team1, null, team1.getGymnasium(), date);
		assertThrows(IllegalArgumentException.class, executable3);
	}
}