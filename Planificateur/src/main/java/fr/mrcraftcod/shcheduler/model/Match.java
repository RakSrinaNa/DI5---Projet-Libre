package fr.mrcraftcod.shcheduler.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class Match{
	private final Team team1;
	private final Team team2;
	private Gymnasium gymnasium;
	private LocalDate date;
	
	public Match(final Team team1, final Team team2, final Gymnasium gymnasium, final LocalDate date){
		if(team1 == null)
			throw new IllegalArgumentException("Match Team 1 is null");
		if(team2 == null)
			throw new IllegalArgumentException("Match Team 2 is null");
		this.team1 = team1;
		this.team2 = team2;
		setGymnasium(gymnasium);
		this.date = date;
	}
	
	public String getCity(){
		if(gymnasium == null)
			return null;
		return gymnasium.getCity();
	}
	
	public LocalDate getDate(){
		return date;
	}
	
	public void setDate(final LocalDate date){
		this.date = date;
	}
	
	public Gymnasium getGymnasium(){
		return gymnasium;
	}
	
	public void setGymnasium(final Gymnasium gymnasium){
		if(gymnasium != null && (!gymnasium.equals(team1.getGymnasium()) && !gymnasium.equals(team2.getGymnasium())))
			throw new IllegalArgumentException("Match gymnasium not corresponding to a team");
		this.gymnasium = gymnasium;
	}
	
	public Team getTeam1(){
		return team1;
	}
	
	public Team getTeam2(){
		return team2;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Match))
			return false;
		Match m = (Match)obj;
		return Objects.equals(m.team1, team1) && Objects.equals(m.team2, team2);
	}
}
