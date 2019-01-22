package fr.mrcraftcod.shcheduler.exceptions;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-22.
 *
 * @author Thomas Couchoud
 * @since 2019-01-22
 */
public class IllegalCSVFormatException extends Exception{
	public IllegalCSVFormatException(final String message){
		super(message);
	}
}
