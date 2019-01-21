package fr.mrcraftcod.shcheduler.model;

import java.time.LocalDate;

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
		this.team1 = team1;
		this.team2 = team2;
		this.gymnasium = gymnasium;
		this.date = date;
	}
	
	public String getCity(){
		return null;
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
		this.gymnasium = gymnasium;
	}
	
	public Team getTeam1(){
		return team1;
	}
	
	public Team getTeam2(){
		return team2;
	}
}
