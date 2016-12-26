package utils.converter;

import java.sql.Timestamp;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import test.util.TimeStampConverter;

@RunWith(MockitoJUnitRunner.class)
public class TimestampConverterTest
{

	private TimeStampConverter timeStampConverter;
	private Timestamp firstTimeStamp;

	@Before
	public void setUp()
	{
		timeStampConverter = new TimeStampConverter();
		timeStampConverter.setDate(2016, 5, 5, 16, 0);
		firstTimeStamp = timeStampConverter.getTimeStamp();
	}

	@Test
	public void shouldReturnDifferenceBetweenTwoTimestampsAndReturnIntNumber()
	{
		timeStampConverter.addDays(2);
		Timestamp secondTimeStamp = timeStampConverter.getTimeStamp();

		double differenceTimeStamp = TimestampConverter.getDifferenceInDays(secondTimeStamp, firstTimeStamp);

		Assert.assertThat(differenceTimeStamp, Matchers.is(2.0));
	}

	@Test
	public void shouldReturnDifferenceBetweenTwoTimestampsAndReturnDoubleNumber()
	{
		timeStampConverter.addDays(2.5);
		Timestamp secondTimeStamp = timeStampConverter.getTimeStamp();

		double differenceTimeStamp = TimestampConverter.getDifferenceInDays(secondTimeStamp, firstTimeStamp);

		Assert.assertThat(differenceTimeStamp, Matchers.is(2.5));

	}

}
