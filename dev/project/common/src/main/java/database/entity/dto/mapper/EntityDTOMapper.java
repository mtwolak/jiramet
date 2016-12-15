package database.entity.dto.mapper;

import java.util.HashSet;
import java.util.Set;

import database.entity.AssignedIssue;
import database.entity.Assignee;
import database.entity.IssueComment;
import database.entity.IssuePriority;
import database.entity.IssueReporter;
import database.entity.IssueResolution;
import database.entity.IssueType;
import database.entity.JiraIssue;
import database.entity.JiraProject;
import database.entity.dto.AssignedIssueDTO;
import database.entity.dto.AssigneeDTO;
import database.entity.dto.IssueCommentDTO;
import database.entity.dto.IssuePriorityDTO;
import database.entity.dto.IssueReporterDTO;
import database.entity.dto.IssueResolutionDTO;
import database.entity.dto.IssueTypeDTO;
import database.entity.dto.JiraIssueDTO;
import database.entity.dto.JiraProjectDTO;

public abstract class EntityDTOMapper
{

	private EntityDTOMapper()
	{

	}

	public static JiraIssueDTO create(JiraIssue jiraIssue)
	{
		JiraIssueDTO jiraIssueDTO = new JiraIssueDTO();
		jiraIssueDTO.setJiraProject(create(jiraIssue.getJiraProject()));
		jiraIssueDTO.setIssueReporter(create(jiraIssue.getIssueReporter()));
		jiraIssueDTO.setIssueType(create(jiraIssue.getIssueType()));
		jiraIssueDTO.setIssueComments(createIssueComment(jiraIssue.getIssueComments()));
		jiraIssueDTO.setIssueResolution(create(jiraIssue.getIssueResolution()));
		jiraIssueDTO.setIssueStatus(jiraIssue.getIssueStatus());
		jiraIssueDTO.setIssuePriority(create(jiraIssue.getIssuePriority()));
		jiraIssueDTO.setSummary(jiraIssue.getSummary());
		jiraIssueDTO.setCode(jiraIssue.getCode());
		jiraIssueDTO.setCreatedAt(jiraIssue.getCreatedAt());
		jiraIssueDTO.setFirstResponseDate(jiraIssue.getFirstResponseDate());
		jiraIssueDTO.setDescription(jiraIssue.getDescription());
		jiraIssueDTO.setPurpose(jiraIssue.getPurpose());
		jiraIssueDTO.setAssignedIssues(createAssignedIssues(jiraIssue.getAssignedIssues()));
		return jiraIssueDTO;
	}

	public static Set<AssignedIssueDTO> createAssignedIssues(Set<AssignedIssue> assignedIssues)
	{
		Set<AssignedIssueDTO> set = new HashSet<>();
		for(AssignedIssue assignedIssue : assignedIssues)
		{
			set.add(create(assignedIssue));
		}
		return set;
	}

	public static AssignedIssueDTO create(AssignedIssue assignedIssue)
	{
		AssignedIssueDTO assignedIssueDTO = new AssignedIssueDTO();
		assignedIssueDTO.setAssignee(create(assignedIssue.getAssignee()));
		assignedIssueDTO.setJiraIssue(create(assignedIssue.getJiraIssue()));
		assignedIssueDTO.setResolvedAt(assignedIssue.getResolvedAt());
		return assignedIssueDTO;
	}

	public static AssigneeDTO create(Assignee assignee)
	{
		AssigneeDTO assigneeDTO = new AssigneeDTO();
		assigneeDTO.setAssignedIssue(createAssignedIssues(assignee.getAssignedIssue()));
		assigneeDTO.setName(assignee.getName());
		return assigneeDTO;
	}

	public static IssuePriorityDTO create(IssuePriority issuePriority)
	{
		IssuePriorityDTO issuePriorityDTO = new IssuePriorityDTO();
		issuePriorityDTO.setJiraIssues(create(issuePriority.getJiraIssues()));
		issuePriorityDTO.setPriorityName(issuePriority.getPriorityName());
		return issuePriorityDTO;
	}

	public static IssueResolutionDTO create(IssueResolution issueResolution)
	{
		IssueResolutionDTO issueResolutionDTO = new IssueResolutionDTO();
		issueResolutionDTO.setResolutionName(issueResolution.getResolutionName());
		issueResolutionDTO.setJiraIssue(create(issueResolution.getJiraIssue()));
		return issueResolutionDTO;
	}

	public static Set<IssueCommentDTO> createIssueComment(Set<IssueComment> issueComments)
	{
		Set<IssueCommentDTO> set = new HashSet<>();
		for (IssueComment issueComment : issueComments)
		{
			set.add(create(issueComment));
		}
		return set;
	}

	public static IssueCommentDTO create(IssueComment issueComment)
	{
		IssueCommentDTO issueCommentDTO = new IssueCommentDTO();
		issueCommentDTO.setAddedAt(issueComment.getAddedAt());
		issueCommentDTO.setAddedBy(issueComment.getAddedBy());
		issueCommentDTO.setContent(issueComment.getContent());
		issueCommentDTO.setJiraIssueNew(create(issueComment.getJiraIssueNew()));
		return issueCommentDTO;
	}

	public static IssueTypeDTO create(IssueType issueType)
	{
		IssueTypeDTO issueTypeDTO = new IssueTypeDTO();
		issueTypeDTO.setTypeName(issueType.getTypeName());
		return issueTypeDTO;
	}

	public static IssueReporterDTO create(IssueReporter issueReporter)
	{
		IssueReporterDTO issueReporterDTO = new IssueReporterDTO();
		issueReporterDTO.setFullName(issueReporter.getFullName());
		return issueReporterDTO;
	}

	public static JiraProjectDTO create(JiraProject jiraProject)
	{
		JiraProjectDTO jiraProjectDTO = new JiraProjectDTO();
		jiraProjectDTO.setJiraProjectId(jiraProject.getJiraProjectId());
		jiraProjectDTO.setProjectName(jiraProject.getProjectName());
		jiraProjectDTO.setJiraIssues(create(jiraProject.getJiraIssues()));
		return jiraProjectDTO;
	}

	public static Set<JiraIssueDTO> create(Set<JiraIssue> jiraIssues)
	{
		Set<JiraIssueDTO> set = new HashSet<>();
		for (JiraIssue jiraIssue : jiraIssues)
		{
			set.add(create(jiraIssue));
		}
		return set;
	}

}
