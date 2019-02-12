package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.model.GroupStage;
import javafx.collections.FXCollections;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupStageTab extends Tab{
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupStageTab.class);
	
	public GroupStageTab(final MainController controller, final GroupStage groupStage){
		super(groupStage.getName());
		
		final var matchPool = FXCollections.observableArrayList(groupStage.getMatches());
		
		final var root = new VBox();
		final var matchesTableView = new MatchTableView(controller);
		matchesTableView.loadGroupStage(groupStage, matchPool);
		
		final var infos = new HBox();
		final var remainingMatchesLabel = new Text("Remaining maches: ");
		final var remainingMatches = new Text();
		remainingMatches.setText("" + matchPool.stream().filter(m -> !m.isAssigned()).count());
		matchPool.forEach(m -> m.assignedProperty().addListener(observable -> {
			remainingMatches.setText("" + matchPool.stream().filter(m2 -> !m2.isAssigned()).count());
		}));
		infos.getChildren().addAll(remainingMatchesLabel, remainingMatches);
		
		root.getChildren().addAll(matchesTableView, infos);
		VBox.setVgrow(matchesTableView, Priority.ALWAYS);
		
		this.setContent(root);
		this.setClosable(false);
	}
}
