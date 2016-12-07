package jira;

import java.util.List;

public interface KnnResolvable {
	
	List<AssigneeTimeResolve> getPrediction(List<AssigneeIssues> jiraIssueSimilarities);

}
