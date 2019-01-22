package fr.mrcraftcod.shcheduler.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-21.
 *
 * @author Thomas Couchoud
 * @since 2019-01-21
 */
public class GroupStage{
	private final Collection<Match> matches;
	private final Collection<Team> teams;
	private final String name;
	
	public GroupStage(final String name){
		this.name = name;
		this.matches = new ArrayList<>();
		this.teams = new ArrayList<>();
	}
	
	public void addMatch(final Match match){
		if(match == null)
			throw new IllegalArgumentException("GroupStage match is null");
		if(matches.contains(match))
			return;
		if(!(teams.contains(match.getTeam1()) && teams.contains(match.getTeam2())))
			throw new IllegalArgumentException("GroupStage match for wrong team");
		this.matches.add(match);
	}
	
	public boolean addAllMatches(final Collection<Match> matches){
		matches.stream().forEach(m -> try{

		}catch(Exception e){

		});
		return this.matches.addAll(matches);
	}
	
	public void addTeam(final Team team){
		if(team == null)
			throw new IllegalArgumentException("GroupStage Team is null");
		if(teams.contains(team))
			return;
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
	public boolean equals(Object obj) {
		if(!(obj instanceof GroupStage))
			return false;
		GroupStage g = (GroupStage)obj;
		return Objects.equals(g.name, name);
	}
}
