package fr.mrcraftcod.scheduler.jfx.table;

import fr.mrcraftcod.scheduler.jfx.MainController;
import fr.mrcraftcod.scheduler.model.GroupStage;
import fr.mrcraftcod.scheduler.model.Gymnasium;
import fr.mrcraftcod.scheduler.model.Match;
import fr.mrcraftcod.scheduler.model.Team;
import fr.mrcraftcod.scheduler.utils.StringUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
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
	private final Stage parentStage;
	
	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 */
	public MatchTableView(final Stage parentStage, final MainController controller){
		super();
		this.parentStage = parentStage;
		this.controller = controller;
		this.setItems(FXCollections.observableList(new ArrayList<>()));
		this.getStylesheets().add(getClass().getResource("/jfx/cell.css").toExternalForm());
		setEditable(true);
		
		setSortPolicy(p -> false);
		getSelectionModel().setCellSelectionEnabled(true);
		this.setStyle("-fx-my-cell-background: -fx-background; -fx-my-cell-text: rgb(80, 80, 80);");
	}
	
	/**
	 * Load the group stage into the table view.
	 *
	 * @param groupStage The group stage to load.
	 * @param matchPool  The matches to assign for this group stage.
	 */
	public void loadGroupStage(final GroupStage groupStage, final ObservableList<Match> matchPool){
		final var colCount = groupStage.getChampionship().getWeeksCount();
		final var padding = 2;
		
		final var columnGymnasium = new TableColumn<Gymnasium, Gymnasium>(StringUtils.getString("gymnasium_column_name"));
		columnGymnasium.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue()));
		columnGymnasium.setCellFactory(col -> new GymnasiumTableCell(parentStage));
		columnGymnasium.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		columnGymnasium.setEditable(false);
		columnGymnasium.setMinWidth(100);
		getColumns().add(columnGymnasium);
		
		groupStage.getChampionship().getDates().stream().sorted().forEach(date -> {
			final var column = new TableColumn<Gymnasium, ObservableList<Match>>(StringUtils.getString("week_column_name", weekFormatter.format(date)));
			column.setCellValueFactory(value -> new SimpleObjectProperty<>(null));
			column.setCellFactory(list -> new GymnasiumMatchTableCell(groupStage, controller, date, matchPool));
			column.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
			column.setEditable(true);
			column.setMinWidth(70);
			getColumns().add(column);
		});
		
		setItems(FXCollections.observableArrayList(groupStage.getTeams().stream().map(Team::getGymnasium).distinct().collect(Collectors.toList())).sorted());
	}
}
