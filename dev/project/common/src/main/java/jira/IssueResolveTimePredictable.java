package jira;

import java.util.List;

public interface IssueResolveTimePredictable {
	
	List<AssigneeTimeResolve> getPrediction(List<AssigneeIssueSimilarity> assigneeSimilarities);

}
