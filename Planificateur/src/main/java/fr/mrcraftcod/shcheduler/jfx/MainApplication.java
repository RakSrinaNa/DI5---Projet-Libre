package fr.mrcraftcod.shcheduler.jfx;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import fr.mrcraftcod.shcheduler.CLIParameters;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.Taskbar;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-17.
 *
 * @author Thomas Couchoud
 * @since 2019-01-17
 */
public class MainApplication extends Application{
	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
	private Stage stage;
	
	@Override
	public void start(final Stage stage) throws Exception{
		this.stage = stage;
		final var scene = buildScene(stage);
		stage.setTitle(this.getFrameTitle());
		stage.setScene(scene);
		stage.sizeToScene();
		if(getIcon() != null){
			setIcon(getIcon());
		}
		stage.show();
		Objects.requireNonNull(this.getOnStageDisplayed()).accept(stage);
	}
	
	@SuppressWarnings("Duplicates")
	private Consumer<Stage> getOnStageDisplayed(){
		return stage -> {
			final var parameters = new CLIParameters();
			try{
				JCommander.newBuilder().addObject(parameters).build().parse(this.getParameters().getRaw().toArray(new String[0]));
			}
			catch(final ParameterException e){
				LOGGER.error("Failed to parse arguments", e);
				e.usage();
				System.exit(1);
			}
			parameters.getCsvConfigFile();
			//TODO: Load data
		};
	}
	
	private void setIcon(final Image icon){
		this.stage.getIcons().clear();
		this.stage.getIcons().add(icon);
		Taskbar.getTaskbar().setIconImage(SwingFXUtils.fromFXImage(icon, null));
	}
	
	public Image getIcon(){
		return null;
	}
	
	public Scene buildScene(final Stage stage){
		return new Scene(createContent(stage), 640, 640);
	}
	
	public String getFrameTitle(){
		return "Shcheduler";
	}
	
	public Parent createContent(final Stage stage){
		final var root = new VBox();
		return root;
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public static void main(final String[] args){
		launch(args);
	}
}
