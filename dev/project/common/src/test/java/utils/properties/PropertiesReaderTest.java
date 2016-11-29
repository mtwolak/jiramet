package utils.properties;

import static org.junit.Assert.*;

import java.util.Properties;

import org.hamcrest.Matchers;
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

	@Test
	public void shouldReturnPropertyAsDouble() throws Exception
	{
		PropertiesReader propertiesReader = getPropertyReader();
		Mockito.when(properties.getProperty(Property.SUMMARY_WEIGHT.name())).thenReturn("0.99");

		assertThat(propertiesReader.getAsDouble(Property.SUMMARY_WEIGHT), Matchers.is(0.99));

	}
	
	@Test
	public void shouldReturnPropertyAsInt() throws Exception
	{
		PropertiesReader propertiesReader = getPropertyReader();
		Mockito.when(properties.getProperty(Property.SUMMARY_WEIGHT.name())).thenReturn("5");

		assertThat(propertiesReader.getAsInt(Property.SUMMARY_WEIGHT), Matchers.is(5));

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
