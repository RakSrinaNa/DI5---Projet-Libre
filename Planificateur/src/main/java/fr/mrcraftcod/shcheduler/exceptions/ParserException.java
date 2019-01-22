package fr.mrcraftcod.shcheduler.exceptions;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-22.
 *
 * @author Thomas Couchoud
 * @since 2019-01-22
 */
public class ParserException extends RuntimeException{
	public ParserException(final String message, final Throwable cause){
		super(message, cause);
	}
}
