package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.model.Championship;
import fr.mrcraftcod.shcheduler.model.Gymnasium;
import fr.mrcraftcod.shcheduler.model.Match;
import java.time.LocalDate;

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
	
	public void setChampionship(Championship championship){
		this.championship = championship;
	}
}
