package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.model.Championship;
import fr.mrcraftcod.shcheduler.model.Gymnasium;
import fr.mrcraftcod.shcheduler.model.Match;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Predicate;

public class MainController{
	private Championship championship;
	
	public MainController(){}
	
	public boolean isGymnasiumFree(Gymnasium gymnasium, LocalDate date){
		return true; //TODO
	}
	
	public void assignMatch(Match item, Gymnasium gymnasium, LocalDate date){
		item.setGymnasium(gymnasium);
		item.setDate(date);
	}
	
	public Predicate<Match> getWeakConstraints(final GymnasiumMatchTableCell gymnasiumMatchTableCell){
		final Predicate<Match> predicateCorrectGymnasium = m -> Objects.equals(gymnasiumMatchTableCell.getGymnasium(), m.getTeam1().getGymnasium());
		
		return predicateCorrectGymnasium.negate();
	}
	
	public void setChampionship(Championship championship){
		this.championship = championship;
	}
}
