package utils.properties;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Properties;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesReaderTest
{

	@Mock
	private Properties properties;
	private PropertiesReader propertiesReader;

	@Before
	public void setUp()
	{
		propertiesReader = getPropertyReader();
	}

	@Test
	public void shouldReturnPropertyAsDouble() throws Exception
	{
		Mockito.when(properties.getProperty(Property.SUMMARY_WEIGHT.name())).thenReturn("0.99");

		assertThat(propertiesReader.getAsDouble(Property.SUMMARY_WEIGHT), Matchers.is(0.99));

	}

	@Test
	public void shouldReturnPropertyAsInt() throws Exception
	{
		Mockito.when(properties.getProperty(Property.SUMMARY_WEIGHT.name())).thenReturn("5");

		assertThat(propertiesReader.getAsInt(Property.SUMMARY_WEIGHT), Matchers.is(5));

	}

	@Test
	public void shouldReturnPropertyBooleanTrueIgnoringCase()
	{
		Mockito.when(properties.getProperty(Property.SHOULD_DOWNLOAD_ALL_ISSUES.name())).thenReturn("TRue");

		assertTrue(propertiesReader.getAsBoolean(Property.SHOULD_DOWNLOAD_ALL_ISSUES));
	}

	@Test
	public void shouldReturnPropertyBooleanFalseIgnoringCase()
	{
		Mockito.when(properties.getProperty(Property.SHOULD_DOWNLOAD_ALL_ISSUES.name())).thenReturn("fALSe");

		assertFalse(propertiesReader.getAsBoolean(Property.SHOULD_DOWNLOAD_ALL_ISSUES));
	}

	@Test
	public void shouldReturnListOfString()
	{
		Mockito.when(properties.getProperty(Property.FILTER_ISSUE_TYPE_LIST.name())).thenReturn("    first, second,third    ");

		List<String> stringList = propertiesReader.getAsStringList(Property.FILTER_ISSUE_TYPE_LIST);
		assertThat(stringList.get(0), Matchers.is("first"));
		assertThat(stringList.get(1), Matchers.is(" second"));
		assertThat(stringList.get(2), Matchers.is("third"));
	}
	
	@Test
	public void shouldReturnEmptyListWhenNoPropertySet()
	{
		Mockito.when(properties.getProperty(Property.FILTER_ISSUE_TYPE_LIST.name())).thenReturn("");

		List<String> stringList = propertiesReader.getAsStringList(Property.FILTER_ISSUE_TYPE_LIST);
		assertThat(stringList.size(), Matchers.is(0));
	}
	
	@Test
	public void shouldReturnOneElementWhenThereIsEntryWithoutComma()
	{
		Mockito.when(properties.getProperty(Property.FILTER_ISSUE_TYPE_LIST.name())).thenReturn("first");

		List<String> stringList = propertiesReader.getAsStringList(Property.FILTER_ISSUE_TYPE_LIST);
		assertThat(stringList.get(0), Matchers.is("first"));
		assertThat(stringList.size(), Matchers.is(1));
	}

	private PropertiesReader getPropertyReader()
	{
		return new PropertiesReader(null)
		{
			@Override
			protected void loadPropertiesFromFile(String propertyPath)
			{
			}

			@Override
			protected Properties getProperties()
			{
				return properties;
			}
		};
	}

}
