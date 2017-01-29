package jira.prediction;

/**
 * Exception when incorrect printing mode was selected
 *
 */
public class IncorrectPredictionPrintModeException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs new exception with unknown, entered printing mode
	 * @param enteredPredictionPrintMode unknown printing mode
	 */
	public IncorrectPredictionPrintModeException(String enteredPredictionPrintMode)
	{
		super("Printing mode for prediction (" + enteredPredictionPrintMode + ") is neither console nor file.");
	}

}
