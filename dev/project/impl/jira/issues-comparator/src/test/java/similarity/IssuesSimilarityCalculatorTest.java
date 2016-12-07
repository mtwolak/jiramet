package similarity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import database.application.DatabaseApplication;
import database.entity.JiraIssue;
import utils.properties.PropertiesReader;

@RunWith(MockitoJUnitRunner.class)
public class IssuesSimilarityCalculatorTest
{
	private IssuesSimilarityCalculator isc;
	@Mock
	private PropertiesReader propertiesReaderMock;
	@Mock
	private DatabaseApplication databaseApplicationMock;
	@Mock
	private IssuesSimilarityHelper issueSimilarityHelperMock;
	@Mock
	private TextSimilarity textsSimilarityMock;

	@Before
	public void setUp()
	{
		isc = new IssuesSimilarityCalculator(propertiesReaderMock, databaseApplicationMock, textsSimilarityMock)
		{
			@Override
			protected IssuesSimilarityHelper getIssueSimilarityHelper()
			{
				return issueSimilarityHelperMock;
			}
		};
		isc.init();
	}


	@Test
	public void shouldGetAssigneeWithIssueSimilarityForExampleIssueCompare()
	{

	}


}
