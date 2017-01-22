package jira;

public interface IssueResolveTimePredictable {
	
	/**
	 * 
	 * @param assigneeSimilarities assignee and set of issues resolved by him with the highest similarity level to the considered issue
	 * @return predicted time needed to resolve the considered issue by the particular developer
	 * @see AssigneeTimeResolve
	 * @see AssigneeIssueSimilarity
	 */
	AssigneeTimeResolve getPrediction(AssigneeIssueSimilarity assigneeSimilarities);

}
