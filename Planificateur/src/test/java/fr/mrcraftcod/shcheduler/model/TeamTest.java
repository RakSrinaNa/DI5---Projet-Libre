package fr.mrcraftcod.shcheduler.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
class TeamTest{
	private Gymnasium gym;
	
	@BeforeEach
	void setUp(){
		this.gym = new Gymnasium("gName", "gCity", Integer.MAX_VALUE);
		;
	}
	
	@Test
	void getGymnasium(){
		final var team = new Team(gym, "tName");
		assertEquals(gym, team.getGymnasium());
	}
	
	@ParameterizedTest()
	@ValueSource(strings = {
			"T1",
			"T2",
			"T3"
	})
	void getName(final String tName){
		final var team = new Team(gym, tName);
		assertEquals(tName, team.getName());
	}
	
	@Test
	void equalsTest(){
		final var team1 = new Team(gym, "tName");
		final var team2 = new Team(gym, "tName");
		assertEquals(team1, team2);
	}
	
	@Test
	void constructEmptyName(){
		final Executable executable1 = () -> new Team(gym, "");
		assertThrows(IllegalArgumentException.class, executable1);
		final Executable executable2 = () -> new Team(gym, null);
		assertThrows(IllegalArgumentException.class, executable2);
		final Executable executable3 = () -> new Team(gym, "   ");
		assertThrows(IllegalArgumentException.class, executable3);
	}
	
	@Test
	void constructEmptyGym(){
		final Executable executable = () -> new Team(null, "gName");
		assertThrows(IllegalArgumentException.class, executable);
	}
	
	@Test
	void getCity(){
		final var team = new Team(gym, "tName");
		assertEquals(gym.getCity(), team.getCity());
	}
}