package fr.mrcraftcod.scheduler.exceptions;

/**
 * Represent an error that stopped the parser.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 2019-01-22.
 *
 * @author Thomas Couchoud
 * @since 2019-01-22
 */
public class ParserException extends RuntimeException{
	private static final long serialVersionUID = 659156061973691126L;
	
	/**
	 * Constructor.
	 *
	 * @param message A description of the exception.
	 * @param cause   The cause of the error.
	 */
	public ParserException(final String message, final Throwable cause){
		super(message, cause);
	}
}
