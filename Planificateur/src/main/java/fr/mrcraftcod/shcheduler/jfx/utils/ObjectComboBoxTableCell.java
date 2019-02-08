package fr.mrcraftcod.shcheduler.jfx.utils;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represent an editable cell in a TableView that update its content when a property is modified and allow changes with a ComboBox.
 * A lot of its content is based ojn {@link javafx.scene.control.cell.ComboBoxTableCell}
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-28
 */
public class ObjectComboBoxTableCell<S, T> extends ObjectTableCell<S, T>{
	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectComboBoxTableCell.class);
	private final static StringConverter<?> defaultStringConverter = new StringConverter<>(){
		@Override
		public String toString(Object t){
			return t == null ? null : t.toString();
		}
		
		@Override
		public Object fromString(String string){
			return string;
		}
	};
	private final StringConverter<T> converter;
	private Predicate<T> warnings;
	private Predicate<T> filter;
	private ObservableList<T> items;
	private ComboBox<T> comboBox;
	
	/**
	 * Constructor.
	 *
	 * @param converter        The converter used for the combo to display the items.
	 * @param items            The items available to select from the combobox.
	 * @param propertyFunction A function giving the property to watch from the item.
	 */
	public ObjectComboBoxTableCell(StringConverter<T> converter, ObservableList<T> items, Function<T, Property> propertyFunction, Predicate<T> filter, Predicate<T> warnings){
		super(propertyFunction);
		InvalidationListener nameChangedListener = observable -> updateItem(getItem(), isEmpty()); //Used to update the displayed value when the employee name is modified
		itemProperty().addListener((observable, oldValue, newValue) -> {
			if(oldValue != null){
				propertyFunction.apply(oldValue).removeListener(nameChangedListener);
			}
			if(newValue != null){
				propertyFunction.apply(newValue).addListener(nameChangedListener);
			}
		});
		this.items = items;
		this.warnings = warnings;
		this.filter = filter;
		//noinspection unchecked
		this.converter = converter == null ? (StringConverter<T>) defaultStringConverter : converter;
	}
	
	@Override
	public void updateItem(T item, boolean empty){
		super.updateItem(item, empty);
		LOGGER.info("i={}, e={}", item, empty);
		if(isEmpty()){
			setText(null);
			setGraphic(null);
		}
		else{
			if(isEditing()){
				if(comboBox != null){
					comboBox.getSelectionModel().select(getItem());
				}
				setText(null);
				setGraphic(comboBox);
			}
			else{
				setText(converter.toString(getItem()));
				setGraphic(null);
			}
		}
	}
	
	@Override
	public void startEdit(){
		if(!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()){
			return;
		}
		
		if(comboBox == null){
			comboBox = createComboBox(this, null, converter);
		}
		
		updateComboBoxList();
		
		super.startEdit();
		setText(null);
		setGraphic(comboBox);
	}
	
	protected void updateComboBoxList(){
		comboBox.setItems(items.filtered(filter).sorted());
		comboBox.getSelectionModel().select(getItem()); //Select the item in the cell
	}
	
	/**
	 * Create the combobox for the selection.
	 *
	 * @param cell      The cell where the combobox will be in.
	 * @param items     The items available for the selection.
	 * @param converter The converter to display the items.
	 *
	 * @return The created combobox.
	 */
	private ComboBox<T> createComboBox(final Cell<T> cell, final ObservableList<T> items, final StringConverter<T> converter){
		ComboBox<T> comboBox = new ComboBox<>(items);
		comboBox.setConverter(converter);
		Callback<ListView<T>, ListCell<T>> cellFactory = param -> new ListCell<>(){
			@Override
			protected void updateItem(final T item, final boolean empty){
				super.updateItem(item, empty);
				if(!empty && item != null){
					setText(item.toString());
					if(Objects.nonNull(ObjectComboBoxTableCell.this.warnings) && ObjectComboBoxTableCell.this.warnings.test(item)){
						setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
					}
					else{
						setBackground(Background.EMPTY);
					}
				}
				else{
					setText(null);
					setBackground(Background.EMPTY);
				}
			}
		};
		comboBox.setButtonCell(cellFactory.call(null));
		comboBox.setCellFactory(cellFactory);
		comboBox.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
			if(cell.isEditing()){
				cell.commitEdit(newValue);
			}
		});
		comboBox.setMaxWidth(Double.MAX_VALUE);
		comboBox.setEditable(false);
		return comboBox;
	}
	
	@Override
	public void cancelEdit(){
		super.cancelEdit();
		
		setText(converter.toString(getItem()));
		setGraphic(null);
	}
	
	protected void setWarnings(Predicate<T> predicate){
		this.warnings = predicate;
	}
	
	protected void setFilter(Predicate<T> filter){this.filter = filter;}
	
	protected void setItems(final ObservableList<T> items){
		this.items = items;
	}
}
