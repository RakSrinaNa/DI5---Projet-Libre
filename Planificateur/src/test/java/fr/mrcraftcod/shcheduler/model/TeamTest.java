package fr.mrcraftcod.shcheduler.model;

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
	@Test
	void getGymnasium(){
		final var gym = new Gymnasium("gName", "gCity", Integer.MAX_VALUE);
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
		final var gym = new Gymnasium("gName", "gCity", Integer.MAX_VALUE);
		final var team = new Team(gym, tName);
		assertEquals(tName, team.getName());
	}
	
	@Test
	void equalsTest(){
		final var gym = new Gymnasium("gName", "gCity", Integer.MAX_VALUE);
		final var team1 = new Team(gym, "tName");
		final var team2 = new Team(gym, "tName");
		assertEquals(team1, team2);
	}
	
	@Test
	void constructEmptyCity(){
		final Executable executable1 = () -> new Gymnasium("gName", "", Integer.MAX_VALUE);
		assertThrows(IllegalArgumentException.class, executable1);
		final Executable executable2 = () -> new Gymnasium("gName", null, Integer.MAX_VALUE);
		assertThrows(IllegalArgumentException.class, executable2);
	}
}