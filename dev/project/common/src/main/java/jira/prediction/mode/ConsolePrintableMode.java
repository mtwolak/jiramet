package jira.prediction.mode;

public class ConsolePrintableMode implements PrintableMode
{

	@Override
	public void print(String textToPrint)
	{
		System.out.println(textToPrint);
	}

}
