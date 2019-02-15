package fr.mrcraftcod.shcheduler.jfx.table;

import fr.mrcraftcod.shcheduler.jfx.EditGymnasiumStage;
import fr.mrcraftcod.shcheduler.model.Gymnasium;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

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
	GymnasiumTableCell(final Stage parentStage){
		this.setAlignment(Pos.CENTER);
		this.setTextAlignment(TextAlignment.CENTER);
		this.setOnMouseClicked(evt -> {
			if(evt.getClickCount() == 2 && evt.getButton() == MouseButton.PRIMARY)
			{
				new EditGymnasiumStage(parentStage, getGymnasium());
			}
		});
	}
	
	/**
	 * Get the gymnasium associated with this cell.
	 *
	 * @return The gymnasium.
	 */
	@SuppressWarnings("Duplicates")
	public Gymnasium getGymnasium(){
		if(this.getTableView().getItems().size() > this.getTableRow().getIndex() && this.getTableRow().getIndex() >= 0){
			return this.getTableView().getItems().get(this.getTableRow().getIndex());
		}
		return null;
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
