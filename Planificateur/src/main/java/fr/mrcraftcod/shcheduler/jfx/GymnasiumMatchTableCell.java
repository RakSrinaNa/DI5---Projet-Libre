package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.jfx.utils.MatchMenuButton;
import fr.mrcraftcod.shcheduler.model.GroupStage;
import fr.mrcraftcod.shcheduler.model.Gymnasium;
import fr.mrcraftcod.shcheduler.model.Match;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Predicate;

public class GymnasiumMatchTableCell extends TableCell<Gymnasium, ObservableList<Match>>{
	private static final Logger LOGGER = LoggerFactory.getLogger(GymnasiumMatchTableCell.class);
	private final ObservableList<Match> matchPool;
	private final Predicate<Match> filters;
	private final Predicate<Match> warnings;
	private final LocalDate date;
	private final MainController controller;
	private ObservableList<Match> matches;
	private MatchMenuButton matchMenuButton;
	private GroupStage groupStage;
	
	public GymnasiumMatchTableCell(final GroupStage gs, final MainController controller, final LocalDate date, final ObservableList<Match> matchPool){
		super();
		this.controller = controller;
		this.groupStage = gs;
		this.matches = null;
		this.matchPool = matchPool;
		this.date = date;
		
		this.filters = controller.getStrongConstraints(this);
		this.warnings = controller.getWeakConstraints(this);
		this.setAlignment(Pos.CENTER);
	}
	
	@Override
	public void updateItem(final ObservableList<Match> item, final boolean empty){
		super.updateItem(item, empty);
		if(!empty){
			if(Objects.nonNull(item)){
				matches = item;
				setGraphic(getCellContent(item));
				setText(null);
				matches.forEach(match -> controller.assignMatch(match, getGymnasium(), getDate()));
			}
			else if(Objects.nonNull(matches)){
				matches.forEach(match -> controller.assignMatch(match, null, null));
				this.setStyle("");
				matches = null;
			}
		}
	}
	
	public Gymnasium getGymnasium(){
		return this.getTableView().getItems().get(this.getTableRow().getIndex());
	}
	
	private Node getCellContent(final ObservableList<Match> matches){
		final var vBox = new VBox(5);
		if(Objects.nonNull(matches)){
			for(final var match : matches){
				if(Objects.nonNull(match)){
					final var group = new FlowPane();
					final var text = new Text();
					setPrefHeight(Control.USE_COMPUTED_SIZE);
					text.wrappingWidthProperty().bind(widthProperty());
					text.setText(match.getTeam1().getName() + "\nVS\n" + match.getTeam2().getName());
					text.setTextAlignment(TextAlignment.CENTER);
					group.setStyle(String.format("-fx-background-color: linear-gradient(to bottom, %s 35%%, %s 65%% 10%%);", match.getTeam1().getGymnasium().getColor(), match.getTeam2().getGymnasium().getColor()));
					group.getChildren().add(text);
					vBox.getChildren().add(group);
				}
			}
		}
		return vBox;
	}
	
	@Override
	public void startEdit(){
		if(!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()){
			return;
		}
		
		if(matchMenuButton == null){
			matchMenuButton = new MatchMenuButton(this, matchPool.filtered(m -> Objects.equals(getGymnasium(), m.getTeam1().getGymnasium()) || Objects.equals(getGymnasium(), m.getTeam2().getGymnasium())), controller);
		}
		
		final var valid = new Button("OK");
		valid.setOnAction(evt -> GymnasiumMatchTableCell.this.commitEdit(matchMenuButton.getCheckedItems()));
		valid.setMaxWidth(Double.MAX_VALUE);
		
		super.startEdit();
		setText(null);
		setGraphic(new VBox(matchMenuButton, valid));
	}
	
	public LocalDate getDate(){
		return date;
	}
	
	@Override
	public void cancelEdit(){
		super.cancelEdit();
		matchMenuButton = null;
		
		setText(null);
		setGraphic(getCellContent(matches));
	}
	
	public GroupStage getGroupStage(){
		return this.groupStage;
	}
}

