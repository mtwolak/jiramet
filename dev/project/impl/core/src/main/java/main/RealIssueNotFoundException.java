package main;

import database.entity.AssignedIssue;

public class RealIssueNotFoundException extends RuntimeException
{

	private static final long serialVersionUID = 8059453797252544085L;

	public RealIssueNotFoundException()
	{
		super("Issue not found!");
	}

	public RealIssueNotFoundException(AssignedIssue assignedIssue)
	{
	}

}
