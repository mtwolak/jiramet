package lucene;

import static org.junit.Assert.*;

import org.junit.Test;

public class CosineTextsSimilarityTest
{

	private static final String TEXT_1 = "Lucene is a text search engine API. Specifically, Lucene is the guts of a search engine - the hard stuff. You write the easy stuff, the UI and the process of selecting and parsing your data files to pump them into the search engine, yourself.";
	private static final String TEXT_2 = "Lucene is a Java full-text search engine. Lucene is not a complete application, but rather a code library and API that can easily be used to add search capabilities to applications.";

	
	@Test
	public void shouldtestTextComparison()
	{
		CosineTextsSimilarity cosineTextsSimilarity = new CosineTextsSimilarity();
		double similarity = cosineTextsSimilarity.getSimilarity(TEXT_1, TEXT_2);
		assertEquals(similarity, 0.43, 0.01);
	}

}
