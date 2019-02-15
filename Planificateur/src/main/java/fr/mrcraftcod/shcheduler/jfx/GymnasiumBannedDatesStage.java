package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.model.GroupStage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Creathed by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-02-15.
 *
 * @author Thomas Couchoud
 * @since 2019-02-15
 */
public class GymnasiumBannedDatesStage{
	public GymnasiumBannedDatesStage(final Stage parentStage, final GroupStage groupStage){
		final var dialog = new Stage();
		
		final var scene = buildScene();
		dialog.setTitle("Banned dates");
		dialog.setScene(scene);
		dialog.sizeToScene();
		
		dialog.initOwner(parentStage);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}
	
	private Scene buildScene(){
		return new Scene(buildContent());
	}
	
	private Parent buildContent(){
		return new VBox();
	}
}
