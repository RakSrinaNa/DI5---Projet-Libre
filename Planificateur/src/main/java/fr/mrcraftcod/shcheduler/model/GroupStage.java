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
		this.matches.add(match);
	}
	
	public void addAllMatches(final Collection<Match> matches){
		this.matches.addAll(matches);
	}
	
	public void addTeam(final Team team){
		this.teams.add(team);
	}
	
	public void addAllTeams(final Collection<Team> teams){
		this.teams.addAll(teams);
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
