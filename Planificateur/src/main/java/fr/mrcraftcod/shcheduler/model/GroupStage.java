package fr.mrcraftcod.shcheduler.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
	private final List<Match> matches;
	private final Collection<Team> teams;
	private final String name;
	private final Championship championship;
	
	/**
	 * Constructor.
	 *
	 * @param championship The championship.
	 * @param name         The name of the group stage.
	 */
	public GroupStage(final Championship championship, final String name){
		this.championship = championship;
		this.name = name;
		this.matches = new ArrayList<>();
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
	@SuppressWarnings("WeakerAccess")
	public boolean addAllMatches(final Collection<Match> matches){
		return matches.stream().filter(Objects::nonNull).allMatch(m -> {
			try{
				this.addMatch(m);
				return true;
			}
			catch(final Exception e){
				return false;
			}
		});
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
	@SuppressWarnings("WeakerAccess")
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
	 * Tell if the group stage contains the given team.
	 *
	 * @param team The team to test for.
	 *
	 * @return True of the team is in this group stage, false otherwise.
	 */
	public boolean containsTeam(final Team team){
		return teams.contains(team);
	}
	
	/**
	 * Get the championship.
	 *
	 * @return The championship.
	 */
	public Championship getChampionship(){
		return this.championship;
	}
	
	/**
	 * Get the name of the group stage.
	 *
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
	public List<Match> getMatches(){
		return this.matches;
	}
	
	/**
	 * Get the teams.
	 *
	 * @return The teams.
	 */
	public Collection<Team> getTeams(){
		return this.teams;
	}
}
