package fr.mrcraftcod.scheduler.jfx.table;

import fr.mrcraftcod.scheduler.jfx.MainController;
import fr.mrcraftcod.scheduler.model.GroupStage;
import fr.mrcraftcod.scheduler.model.Gymnasium;
import fr.mrcraftcod.scheduler.model.Match;
import fr.mrcraftcod.scheduler.utils.StringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Cell displaying matches.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-17.
 *
 * @author Thomas Couchoud
 * @since 2019-01-17
 */
public class GymnasiumMatchTableCell extends TableCell<Gymnasium, ObservableList<Match>>{
	private static final Logger LOGGER = LoggerFactory.getLogger(GymnasiumMatchTableCell.class);
	private final ObservableList<Match> matchPool;
	private final LocalDate date;
	private final MainController controller;
	private ObservableList<Match> matches;
	private MatchMenuButton matchMenuButton;
	private final GroupStage groupStage;
	
	/**
	 * Constructor.
	 *
	 * @param groupStage The group stage the cell belongs to.
	 * @param controller The controller.
	 * @param date       The date this cell represents.
	 * @param matchPool  The matches to assign.
	 */
	public GymnasiumMatchTableCell(final GroupStage groupStage, final MainController controller, final LocalDate date, final ObservableList<Match> matchPool){
		super();
		this.controller = controller;
		this.groupStage = groupStage;
		this.matches = FXCollections.emptyObservableList();
		this.matchPool = matchPool;
		this.date = date;
		
		this.setAlignment(Pos.CENTER);
		this.setPrefHeight(Control.USE_COMPUTED_SIZE);
	}
	
	@Override
	public void updateItem(final ObservableList<Match> item, final boolean empty){
		super.updateItem(item, empty);
		if(!empty){
			if(Objects.nonNull(matches) && !matches.isEmpty()){
				matches.stream().filter(m -> Objects.isNull(item) || !item.contains(m)).forEach(match -> controller.assignMatch(match, null, null));
			}
			if(Objects.nonNull(item)){
				item.forEach(match -> controller.assignMatch(match, getGymnasium(), getDate()));
			}
			matches = item;
			setGraphic(getCellContent(item));
			setText(null);
		}
	}
	
	/**
	 * Get the gymnasium associated with this cell.
	 *
	 * @return The gymnasium.
	 */
	public Gymnasium getGymnasium(){
		//noinspection Duplicates
		if(this.getTableView().getItems().size() > this.getTableRow().getIndex() && this.getTableRow().getIndex() >= 0){
			return this.getTableView().getItems().get(this.getTableRow().getIndex());
		}
		return null;
	}
	
	/**
	 * Builds the content of the cell.
	 *
	 * @param matches The matches to display.
	 *
	 * @return The node to display.
	 */
	private Node getCellContent(final ObservableList<Match> matches){
		final var vBox = new VBox(5);
		if(Objects.nonNull(matches)){
			for(final var match : matches){
				if(Objects.nonNull(match)){
					final var group = new FlowPane();
					final var textM1 = new Text();
					final var textVS = new Text();
					final var textM2 = new Text();
					textM1.wrappingWidthProperty().bind(widthProperty());
					textM1.setText(match.getTeam1().getName());
					textM1.setFill(match.getTeam1().getGymnasium().getColor().getTextColor());
					textM1.setTextAlignment(TextAlignment.CENTER);
					
					textVS.wrappingWidthProperty().bind(widthProperty());
					textVS.setText("\nVS\n");
					textVS.setFill(match.getTeam1().getGymnasium().getColor().getTextColor().interpolate(match.getTeam2().getGymnasium().getColor().getTextColor(), 0.5));
					textVS.setFont(Font.font(textVS.getFont().getFamily(), FontWeight.EXTRA_BOLD, textVS.getFont().getSize()));
					textVS.setTextAlignment(TextAlignment.CENTER);
					
					textM2.wrappingWidthProperty().bind(widthProperty());
					textM2.setText(match.getTeam2().getName());
					textM2.setFill(match.getTeam2().getGymnasium().getColor().getTextColor());
					textM2.setTextAlignment(TextAlignment.CENTER);
					
					group.setStyle(String.format("-fx-background-color: linear-gradient(to bottom, #%s 35%%, #%s 65%% 10%%);", match.getTeam1().getGymnasium().getColor().getBackgroundColor().toString().substring(2), match.getTeam2().getGymnasium().getColor().getBackgroundColor().toString().substring(2)));
					group.getChildren().addAll(textM1, textVS, textM2);
					group.setAlignment(Pos.CENTER);
					group.setPrefHeight(75);
					vBox.getChildren().add(group);
				}
			}
		}
		
		final var prop = controller.remainingPlaceProperty(getGymnasium(), getDate());
		prop.addListener((obs, oldValue, newValue) -> {
			if(Objects.nonNull(matches)){
				if(newValue.intValue() < oldValue.intValue()){
					if(newValue.intValue() < 0){
						if(!matches.isEmpty()){
							final var newMatches = FXCollections.observableArrayList(matches);
							for(var i = 0; i > newValue.intValue(); i--){
								newMatches.remove(newMatches.size() - 1);
							}
							this.updateItem(newMatches, false);
						}
					}
				}
			}
		});
		
		final var group = new FlowPane();
		final var t = new Text();
		t.wrappingWidthProperty().bind(widthProperty().add(-30));
		t.textProperty().bind(prop.asString("%1$+d"));
		t.setTextAlignment(TextAlignment.CENTER);
		
		group.setMaxHeight(Double.MAX_VALUE);
		group.managedProperty().bind(prop.greaterThan(0));
		group.visibleProperty().bind(prop.greaterThan(0));
		group.setAlignment(Pos.CENTER);
		group.getStyleClass().add("places-counter");
		group.getChildren().add(t);
		vBox.getChildren().add(group);
		
		VBox.setVgrow(group, Priority.ALWAYS);
		
		return vBox;
	}
	
	@Override
	public void startEdit(){
		if(!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()){
			return;
		}
		
		this.updateItem(null, false);
		
		if(matchMenuButton == null){
			matchMenuButton = new MatchMenuButton(this, matchPool.filtered(m -> Objects.equals(getGymnasium(), m.getTeam1().getGymnasium()) || Objects.equals(getGymnasium(), m.getTeam2().getGymnasium())), FXCollections.observableArrayList(), controller);
		}
		final var valid = new Button(StringUtils.getString("ok_button"));
		valid.setOnAction(evt -> GymnasiumMatchTableCell.this.commitEdit(Objects.isNull(matchMenuButton) ? matches : matchMenuButton.getCheckedItems()));
		valid.setMaxWidth(Double.MAX_VALUE);
		
		final var vBox = new VBox(matchMenuButton, valid);
		vBox.setOnKeyReleased(t -> {
			if(t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.ESCAPE){
				commitEdit(matchMenuButton.getCheckedItems());
			}
		});
		
		super.startEdit();
		setText(null);
		setGraphic(vBox);
	}
	
	/**
	 * Get the date this cell represents.
	 *
	 * @return The date of the cell.
	 */
	public LocalDate getDate(){
		return date;
	}
	
	@Override
	public void commitEdit(final ObservableList<Match> matches){
		this.matchMenuButton = null;
		super.commitEdit(Objects.isNull(matches) ? FXCollections.emptyObservableList() : matches);
	}
	
	@Override
	public void cancelEdit(){
		this.matchMenuButton = null;
		super.cancelEdit();
		setGraphic(getCellContent(matches));
		setText(null);
	}
	
	/**
	 * Get the group stage the cell represents.
	 *
	 * @return The group stage.
	 */
	@SuppressWarnings("unused")
	public GroupStage getGroupStage(){
		return this.groupStage;
	}
}

