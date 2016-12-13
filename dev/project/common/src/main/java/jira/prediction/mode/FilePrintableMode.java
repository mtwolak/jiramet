package jira.prediction.mode;

import java.io.FileWriter;
import java.io.IOException;

import utils.FileRemover;
import utils.properties.PropertiesReader;
import utils.properties.Property;

public class FilePrintableMode extends PrintableMode
{
	
	@Override
	public void init(PropertiesReader propertiesReader)
	{
		removeOldFileIfExists(propertiesReader.getAsString(Property.PREDICTION_FILE_SAVE_PATH));
	}

	private void removeOldFileIfExists(String predictionFilePath)
	{
		FileRemover.removeIfExists(predictionFilePath);
	}

	@Override
	public void print(String textToPrint, PropertiesReader propertiesReader)
	{
		try
		{
			FileWriter fw = new FileWriter(propertiesReader.getAsString(Property.PREDICTION_FILE_SAVE_PATH), true);
			fw.write(textToPrint);
			fw.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
