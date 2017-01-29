package jira;

import database.entity.Assignee;

/**
 * Holds information about assignee and evaluated prediction time
 *
 */
public class AssigneeTimeResolve
{

	private Assignee assignee;
	private double predictedTime;

	/**
	 * Constructs object with assignee and predicted time
	 * @param assignee jira assignee
	 * @param predictedTime evaluated prediction time
	 */
	public AssigneeTimeResolve(Assignee assignee, double predictedTime)
	{
		this.assignee = assignee;
		this.predictedTime = predictedTime;
	}

	/**
	 * Gets jira assignee
	 * @return jira assignee
	 */
	public Assignee getAssignee()
	{
		return assignee;
	}

	/**
	 * Sets jira assignee
	 * @param assignee jira assignee
	 */
	public void setAssignee(Assignee assignee)
	{
		this.assignee = assignee;
	}

	/**
	 * Gets predicted time
	 * @return predicted time
	 */
	public double getPredictedTime()
	{
		return predictedTime;
	}

	/**
	 * Sets predicted time
	 * @param predictedTime evaluated predition time
	 */
	public void setPredictedTime(double predictedTime)
	{
		this.predictedTime = predictedTime;
	}

}
