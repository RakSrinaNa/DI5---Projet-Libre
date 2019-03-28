package fr.mrcraftcod.scheduler.jfx.table;

import fr.mrcraftcod.scheduler.jfx.MainController;
import fr.mrcraftcod.scheduler.model.Match;
import fr.mrcraftcod.scheduler.utils.StringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Menu button to select matches.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-17.
 *
 * @author Thomas Couchoud
 * @since 2019-01-17
 */
public class MatchMenuButton extends MenuButton{
	private final ObservableList<Match> items;
	private final ObservableList<Match> selected;
	private final Predicate<Match> strong;
	private final Predicate<Match> weak;
	private final GymnasiumMatchTableCell parent;
	
	/**
	 * Constructor.
	 *
	 * @param parent     The parent.
	 * @param items      The items to select.
	 * @param controller The controller.
	 */
	public MatchMenuButton(final GymnasiumMatchTableCell parent, final ObservableList<Match> items, final Collection<Match> selected, final MainController controller){
		super(StringUtils.getString("select_matches_text"));
		this.items = items;
		this.parent = parent;
		this.selected = FXCollections.observableArrayList(selected);
		this.strong = controller.getStrongConstraintsSelection(this);
		this.weak = controller.getWeakConstraintsSelection(this);
		
		items.stream().sorted(Comparator.comparing(Match::getDisplayName)).forEachOrdered(m -> createMatchComboBox(m, controller));
	}
	
	/**
	 * Create a combobox for the given match.
	 *
	 * @param match      The match.
	 * @param controller The controller.
	 */
	private void createMatchComboBox(final Match match, final MainController controller){
		final var checkBox = new CheckBox(match.getDisplayName());
		final var customMenuItem = new CustomMenuItem(checkBox);
		customMenuItem.setHideOnClick(false);
		this.getItems().add(customMenuItem);
		
		if(!controller.getWeakConstraintsSelection(this).test(match)){
			customMenuItem.getStyleClass().add("check-warning");
		}
		checkBox.setDisable(!strong.test(match));
		if(!weak.test(match)){
			customMenuItem.getStyleClass().add("check-warning");
		}
		else{
			customMenuItem.getStyleClass().remove("check-warning");
		}
		
		checkBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
			if(newValue){
				selected.add(match);
			}
			else{
				selected.remove(match);
			}
			
			MatchMenuButton.this.getItems().stream().filter(i -> i instanceof CustomMenuItem).map(i -> ((CustomMenuItem) i).getContent()).filter(i -> i instanceof CheckBox).forEach(i -> {
				final var matchFound = getMatchById(i.getId());
				if(Objects.nonNull(matchFound)){
					i.setDisable(!strong.test(matchFound) && !((CheckBox) i).isSelected());
					if(!weak.test(matchFound)){
						i.getParent().getStyleClass().add("check-warning");
					}
					else{
						i.getParent().getStyleClass().remove("check-warning");
					}
				}
			});
		});
		
		checkBox.setId(match.getId());
	}
	
	/**
	 * Get a match by its id.
	 *
	 * @param id The id of the match.
	 *
	 * @return The match.
	 */
	private Match getMatchById(final String id){
		return items.stream().filter(n -> Objects.equals(n.getId(), id)).findFirst().orElse(null);
	}
	
	/**
	 * Get the checked items.
	 *
	 * @return The checked items.
	 */
	public ObservableList<Match> getCheckedItems(){
		return selected;
	}
	
	/**
	 * Get the parent cell.
	 *
	 * @return The parent cell.
	 */
	public GymnasiumMatchTableCell getParentCell(){
		return this.parent;
	}
}
