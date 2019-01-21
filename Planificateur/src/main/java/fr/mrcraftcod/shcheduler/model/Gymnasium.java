package fr.mrcraftcod.shcheduler.model;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class Gymnasium{
	private final String name;
	private final String city;
	private final int capacity;
	
	public Gymnasium(final String name, final String city, final int capacity){
		this.name = name;
		this.city = city;
		this.capacity = capacity;
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	public String getCity(){
		return city;
	}
	
	public String getName(){
		return name;
	}
}
