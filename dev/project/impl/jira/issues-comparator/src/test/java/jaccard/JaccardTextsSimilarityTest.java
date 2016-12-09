package jaccard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JaccardTextsSimilarityTest
{
	
	private static final String TEXT_1 = "Lucene is a text search engine API. Specifically, Lucene is the guts of a search engine - the hard stuff. You write the easy stuff, the UI and the process of selecting and parsing your data files to pump them into the search engine, yourself.";
	private static final String TEXT_2 = "Lucene is a Java full-text search engine. Lucene is not a complete application, but rather a code library and API that can easily be used to add search capabilities to applications.";

	private double similarity;
	
	@Before
	public void setUp(){
		JaccardTextsSimilarity js = new JaccardTextsSimilarity();
		similarity = js.getSimilarity(TEXT_1, TEXT_2);
	}
	
	@Test
	public void testGetJaccardSimilarity() {
		assertFalse(checkSimilarityCorrectness(similarity) == -1);
	}
	
	private double checkSimilarityCorrectness(double similarity)
	{
		if (similarity < 0 || similarity > 1)
		{
			return -1;
		}
		return similarity;
	}

}
