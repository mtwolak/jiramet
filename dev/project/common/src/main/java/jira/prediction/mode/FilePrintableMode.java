package jira.prediction.mode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FilePrintableMode implements PrintableMode
{

	private static final String FILE_TO_WRITE_TEXT = "../../prediction.txt";

	@Override
	public void print(String textToPrint)
	{
		try
		{
			FileWriter fw = new FileWriter(new File(FILE_TO_WRITE_TEXT));
			fw.write(textToPrint);
			fw.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
