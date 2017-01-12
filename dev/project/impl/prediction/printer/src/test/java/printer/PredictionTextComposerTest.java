package printer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import database.entity.Assignee;
import jira.AssigneeTimeResolve;
import utils.properties.PropertiesReader;
import utils.properties.Property;

@RunWith(MockitoJUnitRunner.class)
public class PredictionTextComposerTest
{

	@Mock
	private PropertiesReader propertiesReader;

	@Before
	public void setup()
	{
		Mockito.when(propertiesReader.getAsString(Property.PREDICTION_PRINTING)).thenReturn("CONSOLE");
	}

	@Test
	public void shouldPrintPredictionToFile()
	{
		PredictionTextComposer predictionTextComposer = new PredictionTextComposer(propertiesReader);
		predictionTextComposer.printPrediction(createAssigneeAndTimes(), 0);
	}

	private AssigneeTimeResolve createAssigneeAndTimes()
	{
		return new AssigneeTimeResolve(createAssignee("Marcin"), 12);
	}

	private Assignee createAssignee(String assigneeName)
	{
		Assignee assignee = new Assignee();
		assignee.setName(assigneeName);
		return assignee;
	}

}
