package assignees;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AssigneeFilterTest {

	@Mock
	private AssigneeFilter assigneeFilter;
	
	@Before
	public void setUp() 
	{
		assigneeFilter = Mockito.mock(AssigneeFilter.class);
	}
	
	@Test
	public void getFilteredAssigneeIssuesList() {
		assertNotNull(assigneeFilter.getFilteredAssigneeIssuesList());
	}

}
