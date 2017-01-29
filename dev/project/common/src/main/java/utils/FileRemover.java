package utils;

import java.io.File;

/**
 * Class for removing files
 *
 */
public abstract class FileRemover
{
	private FileRemover()
	{

	}

	/**
	 * Removes given file
	 * 
	 * @param pathToFile
	 *            path to file which should be removed
	 */
	public static void removeIfExists(String pathToFile)
	{
		new File(pathToFile).delete();
	}
}
