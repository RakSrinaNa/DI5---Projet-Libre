package fr.mrcraftcod.shcheduler.jfx.table;

import fr.mrcraftcod.shcheduler.model.Gymnasium;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.text.TextAlignment;

/**
 * Cell displaying a gymnasium.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-17.
 *
 * @author Thomas Couchoud
 * @since 2019-01-17
 */
public class GymnasiumTableCell extends TableCell<Gymnasium, Gymnasium>{
	/**
	 * Constructor.
	 */
	GymnasiumTableCell(){
		this.setAlignment(Pos.CENTER);
		this.setTextAlignment(TextAlignment.CENTER);
	}
	
	@Override
	protected void updateItem(final Gymnasium item, final boolean empty){
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
