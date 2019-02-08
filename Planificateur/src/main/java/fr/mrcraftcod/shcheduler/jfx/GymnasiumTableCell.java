package fr.mrcraftcod.shcheduler.jfx;

import fr.mrcraftcod.shcheduler.model.Gymnasium;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.text.TextAlignment;

public class GymnasiumTableCell extends TableCell<Gymnasium, Gymnasium>{
	public GymnasiumTableCell(){
		this.setAlignment(Pos.CENTER);
		this.setTextAlignment(TextAlignment.CENTER);
	}
	
	@Override
	protected void updateItem(Gymnasium item, boolean empty){
		super.updateItem(item, empty);
		if(item == null || empty){
			setText(null);
			setStyle("");
		}
		else{
			this.setStyle("-fx-my-cell-background: " + item.getColor() + ";");
			setText(item.getName());
		}
	}
}
