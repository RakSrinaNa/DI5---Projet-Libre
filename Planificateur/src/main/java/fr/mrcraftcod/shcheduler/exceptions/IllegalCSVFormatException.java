package fr.mrcraftcod.shcheduler.exceptions;

/**
 * Represent an error when a CSV file is malformatted.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-22.
 *
 * @author Thomas Couchoud
 * @since 2019-01-22
 */
public class IllegalCSVFormatException extends Exception{
	private static final long serialVersionUID = -7570622850548846217L;
	
	/**
	 * Constructor.
	 *
	 * @param message A description of the error.
	 */
	public IllegalCSVFormatException(final String message){
		super(message);
	}
}
