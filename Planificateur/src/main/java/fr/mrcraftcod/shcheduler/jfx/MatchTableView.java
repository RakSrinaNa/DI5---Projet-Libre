package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.jfx.utils.SortedTableView;
import fr.mrcraftcod.shcheduler.model.GroupStage;
import fr.mrcraftcod.shcheduler.model.Gymnasium;
import fr.mrcraftcod.shcheduler.model.Match;
import fr.mrcraftcod.shcheduler.model.Team;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 24/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-24
 */
public class MatchTableView extends SortedTableView<Gymnasium>{
	private final MainController controller;
	
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
		
		getSelectionModel().setCellSelectionEnabled(true);
		this.setStyle("-fx-my-cell-background: -fx-background;");
	}
	
	public void loadGroupStage(final GroupStage groupStage, ObservableList<Match> matchPool){
		final var colCount = 10;
		final var padding = 2;
		
		final var columnGymnasium = new TableColumn<Gymnasium, Gymnasium>("Gymnasium");
		columnGymnasium.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue()));
		columnGymnasium.setCellFactory(col -> new GymnasiumTableCell());
		columnGymnasium.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		//column.setCellFactory(list -> new ManagerComboBoxTableCell(controller.getCompany().getManagers()));
		columnGymnasium.setEditable(false);
		getColumns().add(columnGymnasium);
		
		for(var i = 1; i <= colCount; i++){
			final var column = new TableColumn<Gymnasium, ObservableList<Match>>("Week " + i);
			column.setCellValueFactory(value -> new SimpleObjectProperty<>(null));
			final var finalI = i;
			column.setCellFactory(list -> new GymnasiumMatchTableCell(groupStage, controller, LocalDate.now().plusDays(finalI * 7), matchPool));
			column.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
			column.setEditable(true);
			getColumns().add(column);
		}
		
		setItems(FXCollections.observableArrayList(groupStage.getTeams().stream().map(Team::getGymnasium).distinct().sorted(Comparator.comparing(Gymnasium::getName)).collect(Collectors.toList())));
	}
}
