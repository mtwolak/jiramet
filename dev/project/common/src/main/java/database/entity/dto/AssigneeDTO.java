package database.entity.dto;

import java.util.Set;


public class AssigneeDTO
{
	private int assigneeId;
	private String name;
	private Set<AssignedIssueDTO> assignedIssue;
	
	public int getAssigneeId()
	{
		return assigneeId;
	}
	public void setAssigneeId(int assigneeId)
	{
		this.assigneeId = assigneeId;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Set<AssignedIssueDTO> getAssignedIssue()
	{
		return assignedIssue;
	}
	public void setAssignedIssue(Set<AssignedIssueDTO> assignedIssue)
	{
		this.assignedIssue = assignedIssue;
	}
	
	
}
