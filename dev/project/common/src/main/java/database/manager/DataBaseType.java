package database.manager;

public enum DataBaseType
{
	PRODUCTION
	{
		@Override
		public String getPathToSettings()
		{
			return "hibernateProduction.cfg.xml";
		}
	},
	TEST
	{
		@Override
		public String getPathToSettings()
		{
			return "hibernateTest.cfg.xml";
		}
	};

	public abstract String getPathToSettings();

}
