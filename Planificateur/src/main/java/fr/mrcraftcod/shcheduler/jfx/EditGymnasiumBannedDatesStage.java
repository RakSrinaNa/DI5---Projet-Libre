package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.model.Gymnasium;
import fr.mrcraftcod.shcheduler.utils.StringUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Creathed by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-02-15.
 *
 * @author Thomas Couchoud
 * @since 2019-02-15
 */
public class EditGymnasiumBannedDatesStage{
	private static final Logger LOGGER = LoggerFactory.getLogger(EditGymnasiumBannedDatesStage.class);
	private final Stage dialog;
	
	public EditGymnasiumBannedDatesStage(final Stage parentStage, final Gymnasium gymnasium){
		dialog = new Stage();
		
		final var scene = buildScene(dialog, gymnasium);
		dialog.setTitle(StringUtils.getString("frame_title_gymnasium_edit_banned_dates", gymnasium.getName()));
		dialog.setScene(scene);
		dialog.sizeToScene();
		
		dialog.initOwner(parentStage);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}
	
	private Scene buildScene(final Stage stage, final Gymnasium gymnasium){
		return new Scene(buildContent(stage, gymnasium));
	}
	
	private Parent buildContent(final Stage stage, final Gymnasium gymnasium){
		final var root = new VBox(3);
		final var popup = new AtomicReference<Popup>(null);
		
		final var datePicker = new DatePicker();
		datePicker.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<>(){
			public DateCell call(final DatePicker datePicker){
				return new DateCell(){
					@Override
					public void updateItem(final LocalDate item, final boolean empty){
						super.updateItem(item, empty);
						final var day = DayOfWeek.from(item);
						if(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY){
							this.setStyle("-fx-background-color: lightblue");
						}
						final var today = LocalDate.now();
						if(item.isEqual(today)){
							this.setStyle("-fx-background-color: green");
						}
						final var selected = gymnasium.getBannedDates().contains(item);
						setDisable(selected);
						if(selected){
							this.setStyle("-fx-background-color: blue");
						}
					}
				};
			}
		};
		datePicker.setDayCellFactory(dayCellFactory);
		datePicker.setOnAction(event -> {
			if(Objects.nonNull(popup.get())){
				popup.get().hide();
			}
			if(!gymnasium.getBannedDates().contains(datePicker.getValue())){
				gymnasium.getBannedDates().add(datePicker.getValue());
			}
		});
		
		final var formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
		final var dateList = new ListView<>(gymnasium.getBannedDates());
		dateList.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		dateList.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.DELETE){
				removeSelectedDates(gymnasium.getBannedDates(), dateList);
			}
		});
		dateList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		dateList.setCellFactory(new Callback<>(){
			@Override
			public ListCell<LocalDate> call(final ListView<LocalDate> param){
				return new ListCell<>(){
					@Override
					protected void updateItem(final LocalDate item, final boolean empty){
						super.updateItem(item, empty);
						if(item != null){
							setText(item.format(formatter));
						}
						else{
							setText(null);
						}
					}
				};
			}
		});
		VBox.setVgrow(dateList, Priority.ALWAYS);
		
		final var addButton = new Button(StringUtils.getString("add_date_button"));
		addButton.setOnAction(event -> {
			popup.set(new Popup());
			popup.get().getContent().add(datePicker);
			popup.get().setAutoHide(true);
			final var window = addButton.getScene().getWindow();
			final var bounds = addButton.localToScene(addButton.getBoundsInLocal());
			final var x = window.getX() + bounds.getMinX();
			final var y = window.getY() + bounds.getMinY() + bounds.getHeight() + 5;
			popup.get().show(addButton, x, y);
			datePicker.show();
		});
		addButton.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(addButton, Priority.ALWAYS);
		
		final var removeButton = new Button(StringUtils.getString("remove_date_button"));
		removeButton.disableProperty().bind(dateList.getSelectionModel().selectedItemProperty().isNull());
		removeButton.setOnAction(event -> removeSelectedDates(gymnasium.getBannedDates(), dateList));
		removeButton.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(removeButton, Priority.ALWAYS);
		
		final var validate = new Button(StringUtils.getString("close_button"));
		validate.setMaxWidth(Double.MAX_VALUE);
		validate.setOnAction(evt -> EditGymnasiumBannedDatesStage.this.dialog.close());
		validate.setDefaultButton(true);
		
		final var buttonBox = new HBox();
		buttonBox.getChildren().addAll(addButton, removeButton);
		
		root.getChildren().addAll(dateList, buttonBox, validate);
		
		return root;
	}
	
	private boolean removeSelectedDates(final Collection<LocalDate> bannedDates, final ListView<LocalDate> dateList){
		return bannedDates.removeAll(dateList.getSelectionModel().getSelectedItems());
	}
}
