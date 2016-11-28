package jira;

import database.entity.Assignee;

public class AssigneeTimeResolve {
	
	private Assignee assignee;
	private double predictedTime;
	
	public Assignee getAssignee()
	{
		return assignee;
	}
	public void setAssignee(Assignee assignee)
	{
		this.assignee = assignee;
	}
	public double getPredictedTime()
	{
		return predictedTime;
	}
	public void setPredictedTime(double predictedTime)
	{
		this.predictedTime = predictedTime;
	}

}
