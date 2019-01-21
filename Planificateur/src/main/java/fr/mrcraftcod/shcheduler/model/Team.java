package fr.mrcraftcod.shcheduler.model;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class Team{
	private final Gymnasium gymnasium;
	private final String name;
	
	public Team(final Gymnasium gymnasium, final String name){
		this.gymnasium = gymnasium;
		this.name = name;
	}
	
	public Gymnasium getGymnasium(){
		return gymnasium;
	}
	
	public String getName(){
		return name;
	}
}
