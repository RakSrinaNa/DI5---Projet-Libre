package fr.mrcraftcod.shcheduler.jfx;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import fr.mrcraftcod.shcheduler.CLIParameters;
import fr.mrcraftcod.shcheduler.Parser;
import fr.mrcraftcod.shcheduler.exceptions.ParserException;
import fr.mrcraftcod.shcheduler.model.GroupStage;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.Taskbar;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Main application window.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-17.
 *
 * @author Thomas Couchoud
 * @since 2019-01-17
 */
public class MainApplication extends Application{
	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
	private Stage stage;
	private MainController controller;
	private TabPane tabPane;
	
	@Override
	public void start(final Stage stage){
		this.stage = stage;
		this.controller = new MainController();
		final var scene = buildScene();
		stage.setTitle(this.getFrameTitle());
		stage.setScene(scene);
		stage.sizeToScene();
		if(getIcon() != null){
			setIcon(getIcon());
		}
		stage.show();
		Objects.requireNonNull(this.getOnStageDisplayed()).accept(stage);
	}
	
	/**
	 * Main call.
	 *
	 * @param args See {@link CLIParameters}.
	 */
	public static void main(final String[] args){
		launch(args);
	}
	
	/**
	 * Build the scene.
	 *
	 * @return The scene.
	 */
	private Scene buildScene(){
		return new Scene(createContent(), 640, 640);
	}
	
	/**
	 * Create the scene content.
	 *
	 * @return The root content.
	 */
	private Parent createContent(){
		tabPane = new TabPane();
		return tabPane;
	}
	
	/**
	 * Get the title of the frame.
	 *
	 * @return The title.
	 */
	private String getFrameTitle(){
		return "Scheduler";
	}
	
	/**
	 * Get the icon to set for the application.
	 *
	 * @return The application icon to set.
	 */
	private Image getIcon(){
		return null;
	}
	
	/**
	 * Set the icon of the application.
	 *
	 * @param icon The icon to set.
	 */
	private void setIcon(final Image icon){
		this.stage.getIcons().clear();
		this.stage.getIcons().add(icon);
		Taskbar.getTaskbar().setIconImage(SwingFXUtils.fromFXImage(icon, null));
	}
	
	/**
	 * Called when the stage is displayed.
	 *
	 * @return The consumer to execute.
	 */
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
			try{
				final var championship = new Parser(';').parse(parameters.getCsvGymnasiumConfigFile(), parameters.getCsvTeamConfigFile());
				controller.setChampionship(championship);
				championship.getGroupStages().stream().sorted(Comparator.comparing(GroupStage::getName)).forEach(gs -> {
					tabPane.getTabs().add(new GroupStageTab(controller, gs));
				});
			}
			catch(final ParserException | IOException e){
				LOGGER.error("Error parsing config", e);
			}
			//TODO: Load data into view
		};
	}
	
	/**
	 * Get the stage.
	 *
	 * @return The stage.
	 */
	public Stage getStage(){
		return stage;
	}
}
