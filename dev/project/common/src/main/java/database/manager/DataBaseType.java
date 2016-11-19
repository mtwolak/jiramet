package database.manager;

public enum DataBaseType
{
	PRODUCTION
	{
		@Override
		public String getPathToSettings()
		{
			return "/resources/hibernateProduction.cfg.xml";
		}
	},
	TEST
	{
		@Override
		public String getPathToSettings()
		{
			return "/resources/hibernateTest.cfg.xml";
		}
	};

	public abstract String getPathToSettings();

}
