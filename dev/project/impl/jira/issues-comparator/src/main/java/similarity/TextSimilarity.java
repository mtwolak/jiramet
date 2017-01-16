package similarity;

import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Defines methods for similarity calculations with taking into account the correctness.
 *
 */
public abstract class TextSimilarity
{
	protected static final Logger LOGGER = Logger.getLogger(TextSimilarity.class.getName());

	protected abstract double processSimilarity(String text1, String text2) throws IOException;

	/**
	 * Contains the method call that calculates texts similarity.
	 * 
	 * @param text1 - text for which we are looking for similarity
	 * @param text2 - text with which we compare
	 * @return similarity between two given texts
	 */
	public double getSimilarity(String text1, String text2)
	{
		checkTextsForNull(text1, text2);
		double similarity = 0.0;
		try
		{
			similarity = processSimilarity(text1, text2);
		} catch (IOException e)
		{
			LOGGER.error(e);
		}
		return checkSimilarityCorrectness(similarity);
	}

	private void checkTextsForNull(String text1, String text2)
	{
		if(text1 == null || text2 == null) {
			throw new UnsupportedOperationException("Cannot process text, when one of texts is null");
		}
	}

	private double checkSimilarityCorrectness(double similarity)
	{
		if (similarity < 0 || similarity > 1)
		{
			LOGGER.error("Similarity " + similarity + "is out of range [0,1]");
			return -1;
		}
		return similarity;
	}

}
