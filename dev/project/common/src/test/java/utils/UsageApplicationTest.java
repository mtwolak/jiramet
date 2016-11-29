package utils;

import static org.junit.Assert.*;

import org.hamcrest.Matchers;
import org.junit.Test;

public class UsageApplicationTest
{

	@Test(expected=InsufficientArgumentsException.class)
	public void shouldThrowExceptionWhenInsufficientNumberOfArgs() throws Exception
	{
		UsageApplication usageApplication = new UsageApplication(new String[] {});
		
		usageApplication.getArg(1);
		
	}
	
	@Test
	public void shouldReturnArgument() throws Exception
	{
		UsageApplication usageApplication = new UsageApplication(new String[] {"First", "Second"});
		
		assertThat(usageApplication.getArg(2), Matchers.is("Second"));
	}
}
