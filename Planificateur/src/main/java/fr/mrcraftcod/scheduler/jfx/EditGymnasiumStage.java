package fr.mrcraftcod.scheduler.jfx;

import fr.mrcraftcod.scheduler.jfx.utils.NumberTextField;
import fr.mrcraftcod.scheduler.model.Gymnasium;
import fr.mrcraftcod.scheduler.utils.StringUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Creathed by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-02-15.
 *
 * @author Thomas Couchoud
 * @since 2019-02-15
 */
public class EditGymnasiumStage{
	private final Stage dialog;
	
	public EditGymnasiumStage(final Stage parentStage, final Gymnasium gymnasium){
		dialog = new Stage();
		
		final var scene = buildScene(dialog, gymnasium);
		dialog.setTitle(StringUtils.getString("frame_title_gymnasium_edit", gymnasium.getName()));
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
		final var root =  new VBox(3);
		
		final var capacityBox = new HBox();
		
		final var capacityInput = new NumberTextField(gymnasium.getCapacity());
		capacityInput.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(capacityInput, Priority.ALWAYS);
		
		capacityBox.getChildren().addAll(new Text(StringUtils.getString("edit_gymnasium_capacity")), capacityInput);
		
		final var bannedDates = new Button(StringUtils.getString("banned_dates_button"));
		bannedDates.setOnAction(evt -> new EditGymnasiumBannedDatesStage(dialog, gymnasium));
		bannedDates.setMaxWidth(Double.MAX_VALUE);
		
		final var validate = new Button(StringUtils.getString("ok_button"));
		validate.setMaxWidth(Double.MAX_VALUE);
		validate.setOnAction(evt -> {
			gymnasium.setCapacity(capacityInput.numberProperty().intValue());
			EditGymnasiumStage.this.dialog.close();
		});
		validate.setDefaultButton(true);
		
		root.getChildren().addAll(capacityBox, bannedDates, validate);
		
		return root;
	}
}
