package fr.mrcraftcod.shcheduler.jfx.table;

import fr.mrcraftcod.shcheduler.jfx.GymnasiumMatchTableCell;
import fr.mrcraftcod.shcheduler.jfx.MainController;
import fr.mrcraftcod.shcheduler.model.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

public class MatchMenuButton extends MenuButton{
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchMenuButton.class);
	private final ObservableList<Match> items;
	private final ObservableList<Match> selected;
	private final Predicate<Match> strong;
	private final Predicate<Match> weak;
	private final GymnasiumMatchTableCell parent;
	
	public MatchMenuButton(final GymnasiumMatchTableCell parent, final ObservableList<Match> items, final MainController controller){
		super("Select matches");
		this.items = items;
		this.parent = parent;
		this.selected = FXCollections.observableArrayList();
		this.strong = controller.getStrongConstraintsSelection(this);
		this.weak = controller.getWeakConstraintsSelection(this);
		
		items.stream().sorted(Comparator.comparing(Match::getDisplayName)).forEachOrdered(m -> createMatchComboBox(m, controller));
	}
	
	private void createMatchComboBox(final Match m, final MainController controller){
		final var checkBox = new CheckBox(m.getDisplayName());
		final var customMenuItem = new CustomMenuItem(checkBox);
		customMenuItem.setHideOnClick(false);
		this.getItems().add(customMenuItem);
		
		if(!controller.getWeakConstraintsSelection(this).test(m)){
			customMenuItem.getStyleClass().add("check-warning");
		}
		checkBox.setDisable(!strong.test(m));
		if(!weak.test(m)){
			customMenuItem.getStyleClass().add("check-warning");
		}
		else{
			customMenuItem.getStyleClass().remove("check-warning");
		}
		
		checkBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
			if(newValue){
				selected.add(m);
			}
			else{
				selected.remove(m);
			}
			
			MatchMenuButton.this.getItems().stream().filter(i -> i instanceof CustomMenuItem).map(i -> ((CustomMenuItem) i).getContent()).filter(i -> i instanceof CheckBox).forEach(i -> {
				final var match = getMatchById(i.getId());
				if(Objects.nonNull(match)){
					i.setDisable(!strong.test(match) && !((CheckBox) i).isSelected());
					if(!weak.test(match)){
						i.getParent().getStyleClass().add("check-warning");
					}
					else{
						i.getParent().getStyleClass().remove("check-warning");
					}
				}
			});
		});
		
		checkBox.setId(m.getId());
	}
	
	private Match getMatchById(final String id){
		return items.stream().filter(n -> Objects.equals(n.getId(), id)).findFirst().orElse(null);
	}
	
	public ObservableList<Match> getCheckedItems(){
		return selected;
	}
	
	public GymnasiumMatchTableCell getParentCell(){
		return this.parent;
	}
}
