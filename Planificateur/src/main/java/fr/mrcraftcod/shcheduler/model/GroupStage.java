package fr.mrcraftcod.shcheduler.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a group stage.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class GroupStage{
	private final ObservableList<Match> matches;
	private final Collection<Team> teams;
	private final String name;
	
	/**
	 * Constructor.
	 *
	 * @param name The name of the group stage.
	 */
	public GroupStage(final String name){
		this.name = name;
		this.matches = FXCollections.observableArrayList(new ArrayList<>());
		this.teams = new ArrayList<>();
	}
	
	/**
	 * Add a collection of matches.
	 * If the match already exists, it won't be added again.
	 *
	 * @param matches The matches to add.
	 *
	 * @return True if all elements were added, false otherwise.
	 */
	public boolean addAllMatches(final Collection<Match> matches){
		//noinspection ReplaceInefficientStreamCount
		return matches.stream().filter(Objects::nonNull).map(m -> {
			try{
				this.addMatch(m);
				return true;
			}
			catch(Exception e){
				return false;
			}
		}).filter(m -> !m).count() == 0;
	}
	
	/**
	 * Add a match.
	 * <p>
	 * If the match is already in the list, it won't be added again.
	 *
	 * @param match The match to add.
	 *
	 * @throws IllegalArgumentException If the match is null or if the teams participating in it are not in this group stage.
	 */
	public void addMatch(final Match match) throws IllegalArgumentException{
		if(Objects.isNull(match)){
			throw new IllegalArgumentException("GroupStage match is null");
		}
		if(!teams.contains(match.getTeam1()) || !teams.contains(match.getTeam2())){
			throw new IllegalArgumentException("GroupStage match for wrong team");
		}
		if(!matches.contains(match)){
			this.matches.add(match);
		}
	}
	
	/**
	 * Add a collection of teams.
	 * If the team already exists, it won't be added again.
	 *
	 * @param teams The teams to add.
	 */
	public void addAllTeams(final Collection<Team> teams){
		teams.stream().filter(Objects::nonNull).forEach(this::addTeam);
	}
	
	/**
	 * Add a team.
	 * <p>
	 * If the team is already in the list, it won't be added again.
	 *
	 * @param team The team to add.
	 *
	 * @throws IllegalArgumentException If the team is null.
	 */
	public void addTeam(final Team team){
		if(team == null){
			throw new IllegalArgumentException("GroupStage Team is null");
		}
		if(!teams.contains(team)){
			this.teams.add(team);
		}
	}
	
	@Override
	public boolean equals(final Object obj){
		return obj instanceof GroupStage && Objects.equals(((GroupStage) obj).getName(), this.getName());
	}
	
	/**
	 * Get the name of the group stage.
	 * @return The name.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Get the matches.
	 *
	 * @return The matches.
	 */
	public ObservableList<Match> getMatches(){
		return this.matches;
	}
	
	/**
	 * Get the teams.
	 * @return The teams.
	 */
	public Collection<Team> getTeams(){
		return this.teams;
	}
}
