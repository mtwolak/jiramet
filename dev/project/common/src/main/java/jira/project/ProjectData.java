package jira.data;

public enum ProjectData
{
	SPRING
	{
		@Override
		public String getProjectURL()
		{
			return "https://jira.spring.io";
		}

		@Override
		public String getProjectKey()
		{
			return "SPR";
		}

		@Override
		public String getProjectName()
		{
			return "Spring Framework";
		}

		@Override
		public int getIssueLimitPerCall()
		{
			return 1000;
		}
	},
	MONGODB
	{
		@Override
		public String getProjectURL()
		{
			return "https://jira.mongodb.org";
		}

		@Override
		public String getProjectKey()
		{
			return "SERVER";
		}

		@Override
		public String getProjectName()
		{
			return "MongoDB Server";
		}
		
		@Override
		public int getIssueLimitPerCall()
		{
			return 500;
		}
	},
	CAMUNDA
	{
		@Override
		public String getProjectURL()
		{
			return "https://app.camunda.com/jira";
		}

		@Override
		public String getProjectKey()
		{
			return "CAM";
		}

		@Override
		public String getProjectName()
		{
			return "Camunda BPM";
		}
		
		@Override
		public int getIssueLimitPerCall()
		{
			return 800;
		}
	};

	public abstract String getProjectURL();

	public abstract String getProjectKey();

	public abstract String getProjectName();
	
	public abstract int getIssueLimitPerCall();

}
