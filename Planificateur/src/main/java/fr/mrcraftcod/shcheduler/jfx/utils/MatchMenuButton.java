package fr.mrcraftcod.shcheduler.jfx.utils;

import fr.mrcraftcod.shcheduler.model.Match;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import java.util.Objects;

public class MatchMenuButton extends MenuButton{
	private final ObservableList<Match> items;
	
	public MatchMenuButton(ObservableList<Match> items){
		super();
		this.items = items;
		for(Match m : items){
			addMatch(m);
		}
		
		items.addListener(new ListChangeListener<Match>(){
			@Override
			public void onChanged(Change<? extends Match> change){
				while(change.next()){
					for(Match m : change.getAddedSubList()){
						addMatch(m);
					}
					for(Match m : change.getRemoved()){
						removeMatch(m);
					}
				}
			}
		});
	}
	
	private void addMatch(Match m){
		CheckBox cb0 = new CheckBox(m.toString());
		CustomMenuItem item0 = new CustomMenuItem(cb0);
		item0.setHideOnClick(false);
		this.getChildren().add(cb0);
		
		cb0.setId(m.getId());
	}
	
	private void removeMatch(Match m){
		getChildren().removeIf(n -> Objects.equals(n.getId(), m.getId()));
	}
	
	public ObservableList<Match> getCheckedItems(){
		return items;
	}
}
