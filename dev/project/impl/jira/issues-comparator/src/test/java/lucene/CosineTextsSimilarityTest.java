package lucene;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CosineTextsSimilarityTest
{

	private static final String TEXT_1 = "Lucene is a text search engine API. Specifically, Lucene is the guts of a search engine - the hard stuff. You write the easy stuff, the UI and the process of selecting and parsing your data files to pump them into the search engine, yourself.";
	private static final String TEXT_2 = "Lucene is a Java full-text search engine. Lucene is not a complete application, but rather a code library and API that can easily be used to add search capabilities to applications.";
	private CosineTextsSimilarity cosineTextsSimilarity;

	@Before
	public void setUp()
	{
		cosineTextsSimilarity = new CosineTextsSimilarity();
	}

	@Test
	public void shouldtestTextComparison()
	{
		double similarity = cosineTextsSimilarity.getSimilarity(TEXT_1, TEXT_2);

		assertEquals(similarity, 0.51, 0.01);
	}

	@Test
	public void shouldReturnOneForTwoSameTextWhenMeasureSimilarity()
	{
		double similarity = cosineTextsSimilarity.getSimilarity(TEXT_1, TEXT_1);

		assertEquals(similarity, 1.0, 0.000001);
	}

	@Test
	public void shouldAvoidStopWords()
	{
		String textWithStopWords = "Here and some or stop and words moreover moreover moreover";
		String textWithoutStopWords = "Here some stop words";

		double similarity = cosineTextsSimilarity.getSimilarity(textWithStopWords, textWithoutStopWords);
		assertEquals(similarity, 1.0, 0.000001);
	}

}
