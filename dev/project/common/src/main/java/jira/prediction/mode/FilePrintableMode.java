package jira.prediction.mode;

import java.io.FileWriter;
import java.io.IOException;

import utils.properties.PropertiesReader;
import utils.properties.Property;

public class FilePrintableMode implements PrintableMode
{

	@Override
	public void print(String textToPrint, PropertiesReader propertiesReader)
	{
		try
		{
			FileWriter fw = new FileWriter(propertiesReader.getAsString(Property.PREDICTION_FILE_SAVE_PATH));
			fw.write(textToPrint);
			fw.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
