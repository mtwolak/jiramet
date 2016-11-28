package jira;

import database.entity.Assignee;

public class AssigneeTimeResolve
{

	private Assignee assignee;
	private double predictedTime;

	public AssigneeTimeResolve(Assignee assignee, double predictedTime)
	{
		this.assignee = assignee;
		this.predictedTime = predictedTime;
	}

	public Assignee getAssignee()
	{
		return assignee;
	}

	public double getPredictedTime()
	{
		return predictedTime;
	}

}
