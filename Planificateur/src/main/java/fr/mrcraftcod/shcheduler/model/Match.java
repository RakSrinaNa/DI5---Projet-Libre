package fr.mrcraftcod.shcheduler.model;

import javafx.beans.property.SimpleBooleanProperty;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a match.
 * <p>
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
	private final SimpleBooleanProperty assignedProperty;
	
	/**
	 * Constructor.
	 * <p>
	 * A match opposing team1 vs team2 isn't the same as a match opposing team2 vs team1.
	 *
	 * @param team1     The first team.
	 * @param team2     The second team.
	 * @param gymnasium The gymnasium the match is in.
	 * @param date      The date of the match.
	 *
	 * @throws IllegalArgumentException If a team is null or if the gymnasium isn't one of the two teams.
	 */
	public Match(final Team team1, final Team team2, final Gymnasium gymnasium, final LocalDate date) throws IllegalArgumentException{
		if(team1 == null){
			throw new IllegalArgumentException("Match Team 1 is null");
		}
		if(team2 == null){
			throw new IllegalArgumentException("Match Team 2 is null");
		}
		this.assignedProperty = new SimpleBooleanProperty(Objects.nonNull(gymnasium) && Objects.nonNull(date));
		this.team1 = team1;
		this.team2 = team2;
		setGymnasium(gymnasium);
		this.date = date;
	}
	
	@Override
	public boolean equals(final Object obj){
		return obj instanceof Match && Objects.equals(((Match) obj).getTeam1(), this.getTeam1()) && Objects.equals(((Match) obj).getTeam2(), this.getTeam2());
	}
	
	/**
	 * Tell if a team is playing in this match.
	 *
	 * @param team The team to test for.
	 *
	 * @return True if the team is playing, false otherwise.
	 */
	public boolean isTeamPlaying(final Team team){
		return Objects.equals(team, team1) || Objects.equals(team, team2);
	}
	
	/**
	 * Set the gymnasium of the match.
	 *
	 * @param gymnasium The gymnasium to set.
	 */
	public void setGymnasium(final Gymnasium gymnasium){
		if(gymnasium != null){
			if(!gymnasium.equals(team1.getGymnasium()) && !gymnasium.equals(team2.getGymnasium())){
				throw new IllegalArgumentException("Match gymnasium not corresponding to a team");
			}
		}
		this.gymnasium = gymnasium;
		assignedProperty.set(Objects.nonNull(gymnasium) && Objects.nonNull(date));
	}
	
	/**
	 * Tell if this match is assigned (i.e the gymnasium and date are set).
	 *
	 * @return True if assigned, false otherwise.
	 */
	public boolean isAssigned(){
		return assignedProperty().get();
	}
	
	/**
	 * Get the display name of the match.
	 *
	 * @return The display name.
	 */
	public String getDisplayName(){
		return getTeam1().getName() + " VS " + getTeam2().getName();
	}
	
	/**
	 * Get the id of the match.
	 *
	 * @return The match id.
	 */
	public String getId(){
		return getTeam1().getName() + getTeam2().getName();
	}
	
	/**
	 * Get the first team.
	 *
	 * @return The first team.
	 */
	public Team getTeam1(){
		return this.team1;
	}
	
	/**
	 * Get the second team.
	 *
	 * @return The second team.
	 */
	public Team getTeam2(){
		return this.team2;
	}
	
	/**
	 * Get the city of the match.
	 *
	 * @return The city, or null if the gymnasium isn't known yet.
	 */
	@SuppressWarnings("WeakerAccess")
	public String getCity(){
		if(this.gymnasium == null){
			return null;
		}
		return this.gymnasium.getCity();
	}
	
	/**
	 * Get the date of the match.
	 *
	 * @return The date, or null if it isn't known yet.
	 */
	public LocalDate getDate(){
		return this.date;
	}
	
	/**
	 * Get the assigned property.
	 *
	 * @return The assigned property.
	 */
	public SimpleBooleanProperty assignedProperty(){
		return this.assignedProperty;
	}
	
	/**
	 * Get the gymnasium of the match.
	 *
	 * @return The gymnasium, or null if it isn't known yet.
	 */
	public Gymnasium getGymnasium(){
		return this.gymnasium;
	}
	
	/**
	 * Set the date of the match.
	 *
	 * @param date The date to set.
	 */
	public void setDate(final LocalDate date){
		this.date = date;
		assignedProperty.set(Objects.nonNull(gymnasium) && Objects.nonNull(date));
	}
	
	@Override
	public String toString(){
		return "Match{" + "team1=" + team1 + ", team2=" + team2 + ", gymnasium=" + gymnasium + ", date=" + date + '}';
	}
}
