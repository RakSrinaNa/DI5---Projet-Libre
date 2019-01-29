package fr.mrcraftcod.shcheduler.model;

import java.time.DayOfWeek;
import java.util.Objects;

/**
 * Represents a team.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class Team{
	private final Gymnasium gymnasium;
	private final String name;
	private final DayOfWeek playDay;
	
	/**
	 * Constructor.
	 *
	 * @param gymnasium The gymnasium the team is associated with.
	 * @param name      The name.
	 *
	 * @param playDay
	 * @throws IllegalArgumentException If the gymnasium is null or if the name is empty.
	 */
	public Team(final Gymnasium gymnasium, final String name, final DayOfWeek playDay) throws IllegalArgumentException{
		if(gymnasium == null){
			throw new IllegalArgumentException("Team gymnasium is empty");
		}
		if(name == null || name.isBlank()){
			throw new IllegalArgumentException("Team name is empty");
		}
		this.gymnasium = gymnasium;
		this.name = name;
		this.playDay = playDay;
	}
	
	@Override
	public boolean equals(final Object obj){
		return obj instanceof Team && Objects.equals(((Team) obj).getName(), this.getName()) && Objects.equals(((Team) obj).getGymnasium(), this.getGymnasium());
	}
	
	/**
	 * Get the name.
	 *
	 * @return The name.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Get the gymnasium.
	 *
	 * @return The gymnasium.
	 */
	public Gymnasium getGymnasium(){
		return this.gymnasium;
	}
	
	/**
	 * Get the city.
	 *
	 * @return The city.
	 */
	public String getCity(){
		return this.gymnasium.getCity();
	}
	
	public DayOfWeek getPlayDay(){
		return playDay;
	}
	
	@Override
	public String toString(){
		return name;
	}
}
