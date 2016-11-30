package lucene;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

public class CosineTextSimilarityTest
{

	private static final String CONTENT = "Content";
	private static final String TEXT_1 = "Lucene is a text search engine API. Specifically, Lucene is the guts of a search engine - the hard stuff. You write the easy stuff, the UI and the process of selecting and parsing your data files to pump them into the search engine, yourself.";
	private static final String TEXT_2 = "Lucene is a Java full-text search engine. Lucene is not a complete application, but rather a code library and API that can easily be used to add search capabilities to applications.";

	private Directory directory = new RAMDirectory();
	private Analyzer analyzer = new SimpleAnalyzer();
	private Document doc = new Document();
	private CosineTextSimilarity cts;

	@Test
	public void testGetCosineTextSimilarity() {
		try
		{
			cts = new CosineTextSimilarity(TEXT_1, TEXT_2);
			assertNotNull(cts.getCosineSimilarity());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testGetTermFrequencies() {
		try
		{
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			IndexWriter writer = new IndexWriter(directory, iwc);
			doc.add(new VecTextField(CONTENT, TEXT_1, Store.YES));
			doc.add(new VecTextField(CONTENT, TEXT_2, Store.YES));
			writer.addDocument(doc);
			writer.close();
			IndexReader reader = DirectoryReader.open(directory);
			cts = new CosineTextSimilarity(TEXT_1, TEXT_2);
			assertNotNull(cts.getTermFrequencies(reader, 0));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
