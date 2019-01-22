package fr.mrcraftcod.shcheduler.model;

import java.util.Objects;

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
		if(gymnasium == null)
			throw new IllegalArgumentException("Team gymnasium is empty");
		if(name == null || name.isBlank())
			throw new IllegalArgumentException("Team name is empty");
		this.gymnasium = gymnasium;
		this.name = name;
	}
	
	public String getCity(){
		return gymnasium.getCity();
	}
	
	public Gymnasium getGymnasium(){
		return gymnasium;
	}
	
	public String getName(){
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Team))
			return false;
		Team t = (Team)obj;
		return Objects.equals(t.name, name) && Objects.equals(t.gymnasium, gymnasium);
	}
}
