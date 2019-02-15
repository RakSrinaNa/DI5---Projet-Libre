package fr.mrcraftcod.shcheduler.jfx.table;

import fr.mrcraftcod.shcheduler.jfx.MainController;
import fr.mrcraftcod.shcheduler.model.GroupStage;
import fr.mrcraftcod.shcheduler.model.Gymnasium;
import fr.mrcraftcod.shcheduler.model.Match;
import fr.mrcraftcod.shcheduler.model.Team;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 24/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-24
 */
public class MatchTableView extends TableView<Gymnasium>{
	private final MainController controller;
	private static final DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("ww");
	
	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 */
	public MatchTableView(final MainController controller){
		super();
		this.controller = controller;
		this.setItems(FXCollections.observableList(new ArrayList<>()));
		this.getStylesheets().add(getClass().getResource("/jfx/cell.css").toExternalForm());
		setEditable(true);
		
		setSortPolicy(p -> true);
		getSelectionModel().setCellSelectionEnabled(true);
		this.setStyle("-fx-my-cell-background: -fx-background;");
	}
	
	/**
	 * Load the group stage into the table view.
	 *
	 * @param groupStage The group stage to load.
	 * @param matchPool  The matches to assign for this group stage.
	 */
	public void loadGroupStage(final GroupStage groupStage, final ObservableList<Match> matchPool){
		final var colCount = 10;
		final var padding = 2;
		
		final var columnGymnasium = new TableColumn<Gymnasium, Gymnasium>("Gymnasium");
		columnGymnasium.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue()));
		columnGymnasium.setCellFactory(col -> new GymnasiumTableCell());
		columnGymnasium.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		columnGymnasium.setEditable(false);
		getColumns().add(columnGymnasium);
		
		groupStage.getChampionship().getDates().stream().sorted().forEach(date -> {
			final var column = new TableColumn<Gymnasium, ObservableList<Match>>("Week " + weekFormatter.format(date));
			column.setCellValueFactory(value -> new SimpleObjectProperty<>(null));
			column.setCellFactory(list -> new GymnasiumMatchTableCell(groupStage, controller, date, matchPool));
			column.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
			column.setEditable(true);
			getColumns().add(column);
		});
		
		setItems(FXCollections.observableArrayList(groupStage.getTeams().stream().map(Team::getGymnasium).distinct().collect(Collectors.toList())).sorted());
	}
}
