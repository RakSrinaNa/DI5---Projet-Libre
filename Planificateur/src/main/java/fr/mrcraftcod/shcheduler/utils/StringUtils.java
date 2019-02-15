package fr.mrcraftcod.shcheduler.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Creathed by mrcraftcod (MrCraftCod - zerderr@gmail.com) on 2019-02-15.
 *
 * @author Thomas Couchoud
 * @since 2019-02-15
 */
public class StringUtils{
	private static StringUtils INSTANCE;
	private ResourceBundle properties;
	
	public static String getString(final String key, final Object... args)
	{
		return MessageFormat.format(getInstance().getResources().getString(key), args);
	}
	
	private ResourceBundle getResources(){
		if(Objects.isNull(this.properties))
		{
			this.properties = ResourceBundle.getBundle("strings.strings", Locale.getDefault());
		}
		return this.properties;
	}
	
	private static StringUtils getInstance(){
		return INSTANCE == null ? (INSTANCE = new StringUtils()) : INSTANCE;
	}
}
