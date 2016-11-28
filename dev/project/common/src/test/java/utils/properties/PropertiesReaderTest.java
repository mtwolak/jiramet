package utils.properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class PropertiesReaderTest
{
	private PropertiesReader preferencesReader;

	@Before
	public void setUp()
	{
		preferencesReader = new PropertiesReader();
	}

	@Test
	public void shouldReadPropertyFromFile()
	{
		String value = preferencesReader.get(Property.ALFA);

		Assert.assertThat(value, is("0.9"));
	}

	@Test(expected = PropertyNotFoundException.class)
	public void shouldThrowPropertyNotFoundExceptionWhenThereIsNoProperty()
	{
		preferencesReader.get(Property.NOT_EXISTING_TEST_PROPERTY);
	}
}
