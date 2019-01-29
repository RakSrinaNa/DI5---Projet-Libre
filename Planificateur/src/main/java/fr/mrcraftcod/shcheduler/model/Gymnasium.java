package fr.mrcraftcod.shcheduler.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Represents a gymnasium.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class Gymnasium{
	private final String name;
	private final String city;
	private final int capacity;
	private final Collection<LocalDate> bannedDates;
	
	/**
	 * Constructor.
	 *
	 * @param name     The name.
	 * @param city     The city.
	 * @param capacity The capacity.
	 *
	 * @throws IllegalArgumentException If the name is empty, or the city is empty or the capacity isn't positive.
	 */
	public Gymnasium(final String name, final String city, final int capacity) throws IllegalArgumentException{
		if(name == null || name.isBlank()){
			throw new IllegalArgumentException("Gymnasium name is empty");
		}
		if(city == null || city.isBlank()){
			throw new IllegalArgumentException("Gymnasium city is empty");
		}
		if(capacity <= 0){
			throw new IllegalArgumentException("Gymnasium capacity is invalid");
		}
		this.name = name;
		this.city = city;
		this.capacity = capacity;
		this.bannedDates = new LinkedList<>();
	}
	
	@Override
	public boolean equals(final Object obj){
		return obj instanceof Gymnasium && Objects.equals(((Gymnasium) obj).getCity(), this.getCity()) && Objects.equals(((Gymnasium) obj).getName(), this.getName());
	}
	
	public void addBannedDate(final LocalDate date){
	
	}
	
	public boolean isDateBanned(final LocalDate date){
		return false;
	}
	
	public Collection<LocalDate> getBannedDates(){
		return bannedDates;
	}
	
	/**
	 * Get the city.
	 *
	 * @return The city.
	 */
	public String getCity(){
		return this.city;
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
	 * Get the capacity.
	 *
	 * @return The capacity.
	 */
	public int getCapacity(){
		return this.capacity;
	}
}
