package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.model.GroupStage;
import fr.mrcraftcod.shcheduler.model.Match;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
		
		final var matchesToAssign = FXCollections.observableArrayList(groupStage.getMatches());
		
		final var root = new VBox();
		final var matchesTableView = new MatchTableView(controller);
		matchesTableView.loadGroupStage(groupStage, matchesToAssign);
		
		final var infos = new HBox();
		final var remainingMatchesLabel = new Text("Remaining maches: ");
		final var remainingMatches = new Text();
		remainingMatches.setText("" + matchesToAssign.size());
		matchesToAssign.addListener((ListChangeListener<Match>) change -> remainingMatches.setText("" + change.getList().size()));
		infos.getChildren().addAll(remainingMatchesLabel, remainingMatches);
		
		root.getChildren().addAll(matchesTableView, infos);
		VBox.setVgrow(matchesTableView, Priority.ALWAYS);
		
		this.setContent(root);
		this.setClosable(false);
	}
}
