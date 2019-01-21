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
class GymnasiumTest{
	
	@ParameterizedTest
	@ValueSource(ints = {
			0,
			1,
			2,
			50,
			Integer.MAX_VALUE
	})
	void getCapacity(final int cap){
		final var gym = new Gymnasium("gName", "gCity", cap);
		assertEquals(cap, gym.getCapacity());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"C1",
			"C2",
			"C3"
	})
	void getCity(final String city){
		final var gym = new Gymnasium("gName", city, Integer.MAX_VALUE);
		assertEquals(city, gym.getCity());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"G1",
			"G2",
			"G3"
	})
	void getName(final String name){
		final var gym = new Gymnasium(name, "gCity", Integer.MAX_VALUE);
		assertEquals(name, gym.getName());
	}
	
	@Test
	void constructEmptyName(){
		final Executable executable1 = () -> new Gymnasium("", "gCity", Integer.MAX_VALUE);
		assertThrows(IllegalArgumentException.class, executable1);
		final Executable executable2 = () -> new Gymnasium(null, "gCity", Integer.MAX_VALUE);
		assertThrows(IllegalArgumentException.class, executable2);
	}
	
	@Test
	void constructEmptyCity(){
		final Executable executable1 = () -> new Gymnasium("gName", "", Integer.MAX_VALUE);
		assertThrows(IllegalArgumentException.class, executable1);
		final Executable executable2 = () -> new Gymnasium("gName", null, Integer.MAX_VALUE);
		assertThrows(IllegalArgumentException.class, executable2);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {
			-1,
			0
	})
	void constructNegativeCapacity(final int cap){
		final Executable executable = () -> new Gymnasium("gName", "gCity", cap);
		assertThrows(IllegalArgumentException.class, executable);
	}
}