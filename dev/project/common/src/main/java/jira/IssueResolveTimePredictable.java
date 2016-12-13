package jira;

public interface IssueResolveTimePredictable {
	
	AssigneeTimeResolve getPrediction(AssigneeIssueSimilarity assigneeSimilarities);

}
