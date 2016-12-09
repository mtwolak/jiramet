package jaccard;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import similarity.TextSimilarity;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class JaccardTextsSimilarity extends TextSimilarity
{
	PropertiesReader propertiesReader;

	public JaccardTextsSimilarity(PropertiesReader propertiesReader)
	{
		this.propertiesReader = propertiesReader;
	}
	
	private Map<String, Integer> getProfile(String string)
	{
		HashMap<String, Integer> shingles = new HashMap<String, Integer>();

		String string_no_space = Pattern.compile("\\s+").matcher(string).replaceAll(" ");
			
		for (int i = 0; i < (string_no_space.length() - propertiesReader.getAsInt(Property.K_SHINGLES) + 1); i++)
		{
			String shingle = string_no_space.substring(i, i + propertiesReader.getAsInt(Property.K_SHINGLES));
			Integer old = shingles.get(shingle);
			if (old != null)
			{
				shingles.put(shingle, old + 1);
			} else
			{
				shingles.put(shingle, 1);
			}
		}

		return Collections.unmodifiableMap(shingles);
	}

	@Override
	protected double processSimilarity(String text1, String text2) throws IOException
	{
		Map<String, Integer> profile1 = getProfile(text1);
		Map<String, Integer> profile2 = getProfile(text2);

		Set<String> union = new HashSet<String>();
		union.addAll(profile1.keySet());
		union.addAll(profile2.keySet());

		int inter = 0;
		double similarity = 0.0;

		for (String key : union)
		{
			if (profile1.containsKey(key) && profile2.containsKey(key))
			{
				inter++;
			}
		}
		similarity = 1.0 * inter / union.size();
		return similarity;
	}

}
