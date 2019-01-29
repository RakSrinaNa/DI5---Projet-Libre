package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.jfx.utils.ObjectComboBoxTableCell;
import fr.mrcraftcod.shcheduler.model.GroupStage;
import fr.mrcraftcod.shcheduler.model.Gymnasium;
import fr.mrcraftcod.shcheduler.model.Match;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Predicate;

public class GymnasiumMatchTableCell extends ObjectComboBoxTableCell<Gymnasium, Match>{
	private final LocalDate date;
	
	public GymnasiumMatchTableCell(final GroupStage gs, MainController controller, LocalDate date){
		super(null, gs.getMatches(), m -> new SimpleStringProperty(m.getTeam1() + " - " + m.getTeam2()), null);
		final Predicate<Match> predicateNotAssigned = m -> Objects.isNull(m.getGymnasium());
		final Predicate<Match> predicateFreeGymnasium = m -> controller.isGymnasiumFree(getGymnasium(), date);
		final Predicate<Match> predicateDontPlaySameDay = m -> gs.getMatches().stream().filter(m2 -> m2.isTeamPlaying(m.getTeam1()) || m2.isTeamPlaying(m.getTeam2())).noneMatch(m2 -> Objects.equals(date, m2.getDate()));
		
		//TODO Contraintes faibles: warning
		final Predicate<Match> predicateCorrectGymnasium = m -> Objects.equals(getGymnasium(), m.getTeam1().getGymnasium()) || Objects.equals(getGymnasium(), m.getTeam2().getGymnasium());
		
		this.setFilter(predicateCorrectGymnasium.and(predicateNotAssigned).and(predicateFreeGymnasium).and(predicateDontPlaySameDay));
		this.date = date;
	}
	
	private Gymnasium getGymnasium(){
		return this.getTableView().getItems().get(this.getTableRow().getIndex());
	}
	
	@Override
	public void updateItem(Match item, boolean empty){
		super.updateItem(item, empty);
		if(!empty && Objects.nonNull(item)){
			item.setGymnasium(getGymnasium());
			item.setDate(getDate());
		}
	}
	
	public LocalDate getDate(){
		return date;
	}
}

