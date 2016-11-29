package jaccard;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class JaccardSimilarity
{
	private static final int K = 3;
	
	public double getJaccardSimilarity(String text1, String text2) {
        Map<String, Integer> profile1 = getProfile(text1);
        Map<String, Integer> profile2 = getProfile(text2);

        Set<String> union = new HashSet<String>();
        union.addAll(profile1.keySet());
        union.addAll(profile2.keySet());

        int inter = 0;

        for (String key : union) {
            if (profile1.containsKey(key) && profile2.containsKey(key)) {
                inter++;
            }
        }

        return 1.0 * inter / union.size();
    }
	
	public double getDistance(String text1, String text2) {
        return 1.0 - getJaccardSimilarity(text1, text2);
    }
	
	public Map<String, Integer> getProfile(String string) {
        HashMap<String, Integer> shingles = new HashMap<String, Integer>();

        String string_no_space = Pattern.compile("\\s+").matcher(string).replaceAll(" ");
        for (int i = 0; i < (string_no_space.length() - K + 1); i++) {
            String shingle = string_no_space.substring(i, i + K);
            Integer old = shingles.get(shingle);
            if (old!=null) {
                shingles.put(shingle, old + 1);
            } else {
                shingles.put(shingle, 1);
            }
        }

        return Collections.unmodifiableMap(shingles);
    }

}
