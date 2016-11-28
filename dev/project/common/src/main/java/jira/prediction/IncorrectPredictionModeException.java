package jira.prediction;

public class IncorrectPredictionModeException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	public IncorrectPredictionModeException(String enteredPredictionMode)
	{
		super("Printing mode for prediction (" + enteredPredictionMode + ") is neither console nor file.");
	}

}
