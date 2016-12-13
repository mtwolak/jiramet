package utils;

import java.io.File;

public abstract class FileRemover
{
	private FileRemover()
	{

	}

	public static void removeIfExists(String pathToFile)
	{
		new File(pathToFile).delete();
	}
}
