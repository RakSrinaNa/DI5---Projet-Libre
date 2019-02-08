package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.jfx.utils.MatchMenuButton;
import fr.mrcraftcod.shcheduler.model.GroupStage;
import fr.mrcraftcod.shcheduler.model.Gymnasium;
import fr.mrcraftcod.shcheduler.model.Match;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.CheckComboBox;
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
	private MatchMenuButton comboBox;
	
	public GymnasiumMatchTableCell(final GroupStage gs, final MainController controller, final LocalDate date, final ObservableList<Match> matchPool){
		super();
		this.controller = controller;
		this.matches = null;
		this.matchPool = matchPool;
		
		final Predicate<Match> predicateMatchGymnasium = m -> Objects.equals(getGymnasium(), m.getTeam1().getGymnasium()) || Objects.equals(getGymnasium(), m.getTeam2().getGymnasium());
		final Predicate<Match> predicateNotAssigned = m -> Objects.isNull(m.getGymnasium());
		final Predicate<Match> predicateFreeGymnasium = m -> controller.isGymnasiumFree(getGymnasium(), date);
		final Predicate<Match> predicateDontPlaySameDay = m -> gs.getMatches().stream().filter(m2 -> m2.isTeamPlaying(m.getTeam1()) || m2.isTeamPlaying(m.getTeam2())).noneMatch(m2 -> Objects.equals(date, m2.getDate()));
		
		this.filters = predicateMatchGymnasium.and(predicateNotAssigned).and(predicateFreeGymnasium).and(predicateDontPlaySameDay);
		this.warnings = controller.getWeakConstraints(this);
		this.date = date;
		this.setAlignment(Pos.CENTER);
	}
	
	public Gymnasium getGymnasium(){
		return this.getTableView().getItems().get(this.getTableRow().getIndex());
	}
	
	@Override
	public void updateItem(final ObservableList<Match> item, final boolean empty){
		super.updateItem(item, empty);
		if(!empty){
			if(Objects.nonNull(item)){
				matches = item;
				setGraphic(getCellContent(item));
				setText(null);
				matches.forEach(match -> assignMatch(match, null, null));
			}
			else if(Objects.nonNull(matches)){
				matches.forEach(match -> assignMatch(match, null, null));
				this.setStyle("");
				matches = null;
			}
		}
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
	
	private void assignMatch(Match match, Gymnasium gymnasium, LocalDate date){
		if(Objects.nonNull(match)){
			match.setGymnasium(gymnasium);
			match.setDate(date);
			if(Objects.isNull(gymnasium) || Objects.isNull(date)){
				matchPool.add(match);
			}
			else{
				matchPool.remove(match);
			}
		}
	}
	
	public LocalDate getDate(){
		return date;
	}
	
	@Override
	public void startEdit(){
		if(!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()){
			return;
		}
		
		if(comboBox == null){
			comboBox = createComboBox(this, matchPool.filtered(filters));
		}
		
		final var valid = new Button("OK");
		valid.setOnAction(evt -> GymnasiumMatchTableCell.this.commitEdit(comboBox.getCheckedItems()));
		valid.setMaxWidth(Double.MAX_VALUE);
		
		super.startEdit();
		setText(null);
		setGraphic(new VBox(comboBox, valid));
	}
	
	private MatchMenuButton createComboBox(final Cell<ObservableList<Match>> cell, final ObservableList<Match> items){
		CheckComboBox<Match> comboBox = new CheckComboBox<>(items);
		// Callback<ListView<T>, ListCell<T>> cellFactory = param -> new ListCell<>(){
		// 	@Override
		// 	protected void updateItem(final T item, final boolean empty){
		// 		super.updateItem(item, empty);
		// 		if(!empty && item != null){
		// 			setText(item.toString());
		// 			if(Objects.nonNull(ObjectComboBoxTableCell.this.warnings) && ObjectComboBoxTableCell.this.warnings.test(item)){
		// 				setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		// 			}
		// 			else{
		// 				setBackground(Background.EMPTY);
		// 			}
		// 		}
		// 		else{
		// 			setText(null);
		// 			setBackground(Background.EMPTY);
		// 		}
		// 	}
		// };
		// //comboBox.setButtonCell(cellFactory.call(null));
		// comboBox.setCellFactory(cellFactory);
		comboBox.setMaxWidth(Double.MAX_VALUE);
		comboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<Match>) change -> {
			while(change.next()){
				if(change.getAddedSize() > 0){
					if(comboBox.getCheckModel().getCheckedIndices().size() > controller.getRemainingSpace(getGymnasium(), getDate())){
						change.getAddedSubList().forEach(m -> Platform.runLater(() -> comboBox.getCheckModel().clearCheck(m)));
					}
				}
			}
		});
		
		final var menuButton = new MatchMenuButton(items);
		return menuButton;
	}
	
	@Override
	public void cancelEdit(){
		super.cancelEdit();
		comboBox = null;
		
		setText(null);
		setGraphic(getCellContent(matches));
	}
}

