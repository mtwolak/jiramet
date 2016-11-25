package prediction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import database.application.DatabaseApplication;
import database.entity.AssignedIssue;
import database.entity.Assignee;
import database.entity.JiraIssue;
import database.entity.JiraProject;
import javafx.util.Pair;

//tests not working
@Deprecated
public class IssueResolvingTimes
{

	private DatabaseApplication dba;
	private double alphaMin;
	private int k;
	private ArrayList<ResolvingTimesStructure> rtsList;

	public IssueResolvingTimes()
	{
		this.dba = new DatabaseApplication();
		this.alphaMin = 0.5;
		this.k = 5;
		this.rtsList = new ArrayList<ResolvingTimesStructure>();
	}

	public List<Pair<Integer, Double>> getIssueResolvingTimesPrediction(JiraIssue issue,
			List<Pair<Integer, Double>> similarityList)
	{
		JiraProject project = issue.getJiraProject();
		@SuppressWarnings("unchecked")
		List<Assignee> assigneeList = this.dba.getAssigneeList(project);
		for (Assignee assignee : assigneeList)
		{
			this.rtsList.add(new ResolvingTimesStructure(assignee.getAssigneeId()));
			this.rtsList.get(this.rtsList.size() - 1)
					.setSimilarities(getAssigneeSimilarities(project, assignee.getAssigneeId(), similarityList));
			List<Pair<Integer, Double>> issuesResolvingTimes = new ArrayList<Pair<Integer, Double>>();
			issuesResolvingTimes = getIssuesResolvingTimes(project, assignee.getAssigneeId());
			this.rtsList.get(this.rtsList.size() - 1).setIssuesResolvingTimes(issuesResolvingTimes);
		}
		this.sortRTSListSimilarities();

		List<Pair<Integer, Double>> issueResolvingTimesPrediction = new ArrayList<Pair<Integer, Double>>();
		for (ResolvingTimesStructure rts : this.rtsList)
		{
			double alphaSum = 0;
			double issueTimePrediction = 0;
			int it = 0;
			for (Pair<Integer, Double> a : rts.getSimilarities())
			{
				alphaSum += a.getValue();
				issueTimePrediction += a.getValue() * rts.getIssueResolvingTimeValueByID(a.getKey());
				it++;
				if (it == this.k || it == rts.getSimilarities().size())
				{
					issueResolvingTimesPrediction
							.add(new Pair<Integer, Double>(rts.getAssigneeID(), issueTimePrediction / alphaSum));
					break;
				}

			}
		}
		this.dba.closeSession();
		return issueResolvingTimesPrediction;
	}

	@SuppressWarnings("restriction")
	public List<Pair<Integer, Double>> getAssigneeSimilarities(JiraProject project, int assigneeID,
			List<Pair<Integer, Double>> similarityList)
	{
		@SuppressWarnings("unchecked")
		List<AssignedIssue> assignedIssues = this.dba.getAssignedIssues(project);
		List<Pair<Integer, Double>> assigneeSimilarities = new ArrayList<Pair<Integer, Double>>();
		for (AssignedIssue assigned : assignedIssues)
		{ // get assignedIssues where assigned.assigneeID = assigneeID
			if (assigneeID == assigned.getAssignee().getAssigneeId())
			{
				for (Pair<Integer, Double> i : similarityList)
					if (i.getKey() == assigned.getJiraIssue().getJiraIssueId() && i.getValue() > this.alphaMin)
					{
						assigneeSimilarities.add(i);
						break;
					}
			}
		}
		if (assigneeSimilarities.isEmpty())
		{
			return null;
		} else
		{
			return assigneeSimilarities;
		}
	}

	@SuppressWarnings("restriction")
	public List<Pair<Integer, Double>> getIssuesResolvingTimes(JiraProject project, int assigneeID)
	{
		@SuppressWarnings("unchecked")
		List<Pair<Integer, Double>> issuesResolvingTimes = new ArrayList<Pair<Integer, Double>>();
		@SuppressWarnings("unchecked")
		List<AssignedIssue> assignedIssues = this.dba.getAssignedIssues(project);
		for (Pair<Integer, Double> i : rtsList.get(rtsList.size() - 1).getSimilarities())
		{
			for (AssignedIssue assigned : assignedIssues)
			{ // assigned = get assigned where assigned.JiraissueID = i.getkey()
				if (assigned.getJiraIssue().getJiraIssueId() == i.getKey())
				{
					Timestamp finish = assigned.getResolvedAt();
					Timestamp start = assigned.getJiraIssue().getCreatedAt();
					issuesResolvingTimes.add(new Pair<Integer, Double>(i.getKey(),
							((finish.getTime() - start.getTime()) * 1.0) / (1000 * 60 * 60 * 24)));
				}
			}
		}
		if (issuesResolvingTimes.isEmpty())
		{
			return null;
		} else
		{
			return issuesResolvingTimes;
		}
	}

	public void sortRTSListSimilarities()
	{
		for (int i = 0; i < this.rtsList.size(); i++)
		{
			Collections.sort(this.rtsList.get(i).getSimilarities(), new Comparator<Pair<Integer, Double>>()
			{

				@Override
				public int compare(Pair<Integer, Double> o1, Pair<Integer, Double> o2)
				{
					return (int) (o2.getValue() - o1.getValue());
				}
			});
		}
	}
}
