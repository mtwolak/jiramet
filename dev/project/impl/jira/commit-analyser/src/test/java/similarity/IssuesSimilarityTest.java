package similarity;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import javafx.util.Pair;

public class IssuesSimilarityTest {

	IssuesSimilarity is = new IssuesSimilarity();	

	@Test
	public void getSimilarityListTest() {
		int issueID = 1;
		/*List<Pair<Integer, Double>> result = is.getIssueSimilarityList(issueID);*/
		assertNotNull(issueID);
	}
	
	@Test
	public void getIssueSimilarityTest() 
	{
		double result = -1.0;
		int issueID = 1;
		assertNotEquals(result, -1.0);
	}

}
