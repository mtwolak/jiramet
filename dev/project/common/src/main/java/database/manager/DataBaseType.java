package database.manager;

public enum DataBaseType
{
	PRODUCTION("hibernateProduction.cfg.xml"), TEST("hibernateTest.cfg.xml");

	private String configurationFilename;

	private DataBaseType(String configurationFilename)
	{
		this.configurationFilename = configurationFilename;
	}

	public String getSettingsPath()
	{
		return HIBERNATE_PATH + configurationFilename;
	}

	private static final String HIBERNATE_PATH = "/resources/hibernate/";

}
