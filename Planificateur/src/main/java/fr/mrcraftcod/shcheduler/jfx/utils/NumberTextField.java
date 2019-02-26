package fr.mrcraftcod.shcheduler.jfx.utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;

/**
 * Creathed by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-02-15.
 *
 * @author Thomas Couchoud
 * @since 2019-02-15
 */
public class NumberTextField extends TextField{
	private final SimpleIntegerProperty number = new SimpleIntegerProperty(1);
	
	public NumberTextField(int defaultValue){
		super(Integer.toString(defaultValue));
		number.set(defaultValue);
	}
	
	@Override
	public void replaceText(final int start, final int end, final String text){
		if(validate(text)){
			super.replaceText(start, end, text);
			number.set(Integer.parseInt(getText()));
		}
	}
	
	@Override
	public void replaceSelection(final String text){
		if(validate(text)){
			super.replaceSelection(text);
			number.set(Integer.parseInt(getText()));
		}
	}
	
	private boolean validate(final String text){
		return text.matches("[0-9]*");
	}
	
	public SimpleIntegerProperty numberProperty(){
		return number;
	}
}
