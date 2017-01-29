package jira.prediction.mode;

import java.io.FileWriter;
import java.io.IOException;

import utils.FileRemover;
import utils.properties.PropertiesReader;
import utils.properties.Property;

/**
 * Implementation of printing date to output file
 *
 */
public class FilePrintableMode extends PrintableMode
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(PropertiesReader propertiesReader)
	{
		removeOldFileIfExists(propertiesReader.getAsString(Property.PREDICTION_FILE_SAVE_PATH));
	}

	private void removeOldFileIfExists(String predictionFilePath)
	{
		FileRemover.removeIfExists(predictionFilePath);
	}

	/**
	 * {@inheritDoc}
	 */
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
