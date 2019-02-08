package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.jfx.utils.ObjectComboBoxTableCell;
import fr.mrcraftcod.shcheduler.model.GroupStage;
import fr.mrcraftcod.shcheduler.model.Gymnasium;
import fr.mrcraftcod.shcheduler.model.Match;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Predicate;

public class GymnasiumMatchTableCell extends ObjectComboBoxTableCell<Gymnasium, Match>{
	private final ObservableList<Match> matchPool;
	private Match match;
	private final LocalDate date;
	
	public GymnasiumMatchTableCell(final GroupStage gs, final MainController controller, final LocalDate date, final ObservableList<Match> matchPool){
		super(null, matchPool, m -> new SimpleStringProperty(m.getTeam1() + " - " + m.getTeam2()), null, null);
		this.match = null;
		this.matchPool = matchPool;
		
		final Predicate<Match> predicateMatchGymnasium = m -> Objects.equals(getGymnasium(), m.getTeam1().getGymnasium()) || Objects.equals(getGymnasium(), m.getTeam2().getGymnasium());
		final Predicate<Match> predicateNotAssigned = m -> Objects.isNull(m.getGymnasium());
		final Predicate<Match> predicateFreeGymnasium = m -> controller.isGymnasiumFree(getGymnasium(), date);
		final Predicate<Match> predicateDontPlaySameDay = m -> gs.getMatches().stream().filter(m2 -> m2.isTeamPlaying(m.getTeam1()) || m2.isTeamPlaying(m.getTeam2())).noneMatch(m2 -> Objects.equals(date, m2.getDate()));
		
		this.setFilter(predicateMatchGymnasium.and(predicateNotAssigned).and(predicateFreeGymnasium).and(predicateDontPlaySameDay));
		this.setWarnings(controller.getWeakConstraints(this));
		this.date = date;
		this.setAlignment(Pos.CENTER);
	}
	
	public Gymnasium getGymnasium(){
		return this.getTableView().getItems().get(this.getTableRow().getIndex());
	}
	
	@Override
	public void updateItem(final Match item, final boolean empty){
		super.updateItem(item, empty);
		if(!empty){
			if(Objects.nonNull(item)){
				match = item;
				this.setStyle(String.format("-fx-my-cell-background: linear-gradient(to bottom, %s 35%%, %s 65%% 10%%);", match.getTeam1().getGymnasium().getColor(), match.getTeam2().getGymnasium().getColor()));
				
				var text = new Text();
				setGraphic(text);
				setText(null);
				setPrefHeight(Control.USE_COMPUTED_SIZE);
				text.wrappingWidthProperty().bind(widthProperty());
				text.setText(item.getTeam1().getName() + "\nVS\n" + item.getTeam2().getName());
				text.setTextAlignment(TextAlignment.CENTER);
				
				assignMatch(item, getGymnasium(), date);
			}
			else if(Objects.nonNull(match)){
				assignMatch(match, null, null);
				this.setStyle("");
				this.updateComboBoxList();
				match = null;
			}
		}
	}
	
	private void assignMatch(Match match, Gymnasium gymnasium, LocalDate date){
		match.setGymnasium(gymnasium);
		match.setDate(date);
		if(Objects.isNull(gymnasium) || Objects.isNull(date)){
			matchPool.add(match);
		}
		else{
			matchPool.remove(match);
		}
	}
	
	public LocalDate getDate(){
		return date;
	}
}

