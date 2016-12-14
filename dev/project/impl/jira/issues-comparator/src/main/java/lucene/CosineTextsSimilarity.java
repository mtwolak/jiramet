package lucene;

import java.io.IOException;
import java.util.*;

import org.apache.commons.math3.linear.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

import similarity.TextSimilarity;

public class CosineTextsSimilarity extends TextSimilarity
{

	public static final String CONTENT = "Content";
	private final Set<String> terms = new HashSet<>();
	private RealVector v1;
	private RealVector v2;
	private static final Analyzer ANALYZER = new StandardAnalyzer(LuceneStopWords.generate());

	private void initVectors(String s1, String s2) throws IOException
	{
		Directory directory = createIndex(s1, s2);
		IndexReader reader = DirectoryReader.open(directory);
		Map<String, Integer> f1 = getTermFrequencies(reader, 0);
		Map<String, Integer> f2 = getTermFrequencies(reader, 1);
		reader.close();
		v1 = toRealVector(f1);
		v2 = toRealVector(f2);
	}

	private Directory createIndex(String text1, String text2) throws IOException
	{
		Directory directory = new RAMDirectory();
		IndexWriterConfig iwc = new IndexWriterConfig(ANALYZER);
		IndexWriter writer = new IndexWriter(directory, iwc);
		addDocument(writer, text1);
		addDocument(writer, text2);
		writer.close();
		return directory;
	}

	private void addDocument(IndexWriter writer, String content) throws IOException
	{
		Document doc = new Document();
		doc.add(new VecTextField(CONTENT, content, Store.YES));
		writer.addDocument(doc);
	}

	private double getSimilarityFromVectors()
	{
		return (v1.dotProduct(v2)) / (v1.getNorm() * v2.getNorm());
	}

	@Override
	protected double processSimilarity(String text1, String text2) throws IOException
	{
		initVectors(text1, text2);
		return getSimilarityFromVectors();
	}

	private Map<String, Integer> getTermFrequencies(IndexReader reader, int docId) throws IOException
	{
		Terms vector = reader.getTermVector(docId, CONTENT);
		if(vector == null) {
			return new HashMap<>();
		}
		TermsEnum termsEnum = null;
		termsEnum = vector.iterator();
		Map<String, Integer> frequencies = new HashMap<>();
		BytesRef text = null;
		while ((text = termsEnum.next()) != null)
		{
			String term = text.utf8ToString();
			int freq = (int) termsEnum.totalTermFreq();
			frequencies.put(term, freq);
			terms.add(term);
		}
		return frequencies;
	}

	private RealVector toRealVector(Map<String, Integer> map)
	{
		RealVector vector = new ArrayRealVector(terms.size());
		int i = 0;
		for (String term : terms)
		{
			int value = map.containsKey(term) ? map.get(term) : 0;
			vector.setEntry(i++, value);
		}
		return (RealVector) vector.mapDivide(vector.getL1Norm());
	}

}
