package fr.mrcraftcod.shcheduler.model;

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
	private final Collection<Match> matches;
	private final Collection<Team> teams;
	private final String name;
	
	/**
	 * Constructor.
	 *
	 * @param name The name of the group satge.
	 */
	public GroupStage(final String name){
		this.name = name;
		this.matches = new ArrayList<>();
		this.teams = new ArrayList<>();
	}
	
	/**
	 * Add a collection matches.
	 * If the match already exists, it won't be added again.
	 *
	 * @param matches The matches to add.
	 *
	 * @return True if all elements were added, false otherwise.
	 */
	public boolean addAllMatches(final Collection<Match> matches){
		//noinspection ReplaceInefficientStreamCount
		return matches.stream().filter(m -> m != null).map(m -> {
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
		if(match == null){
			throw new IllegalArgumentException("GroupStage match is null");
		}
		if(matches.contains(match)){
			return;
		}
		if(!(teams.contains(match.getTeam1()) && teams.contains(match.getTeam2()))){
			throw new IllegalArgumentException("GroupStage match for wrong team");
		}
		this.matches.add(match);
	}
	
	public void addTeam(final Team team){
		if(team == null){
			throw new IllegalArgumentException("GroupStage Team is null");
		}
		if(teams.contains(team)){
			return;
		}
		this.teams.add(team);
	}
	
	public void addAllTeams(final Collection<Team> teams){
		teams.stream().forEach(t -> this.addTeam(t));
	}
	
	public Collection<Match> getMatches(){
		return matches;
	}
	
	public String getName(){
		return name;
	}
	
	public Collection<Team> getTeams(){
		return teams;
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof GroupStage)){
			return false;
		}
		GroupStage g = (GroupStage) obj;
		return Objects.equals(g.name, name);
	}
}
