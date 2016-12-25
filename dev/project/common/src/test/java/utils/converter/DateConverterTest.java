package utils.converter;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import utils.converter.DateConverter;

import static org.hamcrest.CoreMatchers.instanceOf;

public class DateConverterTest
{
	private DateFormat testFormat;
	private DateFormat wrongFormat;
	private String simpleDate;
	
	@Before
	public void setUp() {
		testFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		wrongFormat = new SimpleDateFormat("yyyy-MM-ddX");
		simpleDate = "2016-12-12 21:12:35.643";
	}

	@Test
	public void testNullStringConvert()
	{
		assertNull(DateConverter.convertStringToTimestamp(null, testFormat));
	}
	
	@Test
	public void testNullFormatConvert()
	{
		assertNull(DateConverter.convertStringToTimestamp(simpleDate, null));
	}
	
	@Test
	public void testWrongFormatConvert()
	{
		assertNull(DateConverter.convertStringToTimestamp(simpleDate, wrongFormat));
	}
	
	@Test
	public void testCorrectFormatConvert()
	{
		assertThat(DateConverter.convertStringToTimestamp(simpleDate, testFormat), instanceOf(Timestamp.class));
	}

}
