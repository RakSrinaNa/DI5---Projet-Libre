package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.model.Gymnasium;
import fr.mrcraftcod.shcheduler.utils.StringUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Collection;

/**
 * Creathed by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-02-15.
 *
 * @author Thomas Couchoud
 * @since 2019-02-15
 */
public class EditGymnasiumListStage{
	public EditGymnasiumListStage(final Stage parentStage, final Collection<Gymnasium> gymnasiums){
		final var dialog = new Stage();
		
		final var scene = buildScene(dialog, gymnasiums);
		dialog.setTitle(StringUtils.getString("frame_title_gymnasium_list"));
		dialog.setScene(scene);
		dialog.sizeToScene();
		
		dialog.initOwner(parentStage);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}
	
	private Scene buildScene(final Stage stage, final Collection<Gymnasium> gymnasiums){
		return new Scene(buildContent(stage, gymnasiums));
	}
	
	private Parent buildContent(final Stage stage, final Collection<Gymnasium> gymnasiums){
		final var root =  new VBox(3);
		
		gymnasiums.stream().sorted().forEachOrdered(gymnasium -> {
			final var button = new Button(gymnasium.getName());
			button.setMaxWidth(Double.MAX_VALUE);
			button.setOnAction(evt -> new EditGymnasiumStage(stage, gymnasium));
			HBox.setHgrow(button, Priority.ALWAYS);
			root.getChildren().add(button);
		});
		
		return root;
	}
}
