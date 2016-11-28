package jira;

import database.entity.Assignee;

public class AssigneeIssueTime
{
	private Assignee assignee;
	private double resolveTime;
	
	public AssigneeIssueTime(Assignee assignee, double resolveTime) {
		this.assignee = assignee;
		this.resolveTime = resolveTime;
	}
	
	public Assignee getAssignee() {
		return assignee;
	}
	
	public double getResolveTime() {
		return resolveTime;
	}

}
