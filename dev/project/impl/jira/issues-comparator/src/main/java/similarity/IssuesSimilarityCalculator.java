package similarity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.entity.JiraIssue;
import jira.AssigneeIssueSimilarity;
import jira.AssigneeIssues;
import jira.IssuesSimilarity;
import jira.JiraIssueSimilarity;
import utils.properties.PropertiesReader;
import utils.properties.Property;

/**
 * Contains methods responsible for calculating issues similarity used in
 * prediction.
 *
 */
public class IssuesSimilarityCalculator implements IssuesSimilarity
{
	private IssuesSimilarityCommentsCollector issuesSimilarityCommentsCollector;
	private TextSimilarity weedOutStrategy;
	private TextSimilarity textsSimilarity;
	private PropertiesReader propertiesReader;
	private ListPartitioner listPartitioner;
	private double alfa;

	/**
	 * Creates a new instance of IssuesSimilarityCalculator class and
	 * initializes all necessary variables.
	 * 
	 * @param propertiesReader
	 *            - properties reader, grants access to system configuration
	 *            variables
	 * @param weedOutStrategy
	 *            - method used to weed out issues with small similarity
	 * @param textsSimilarityStrategy
	 *            - method used for issues similarity calculations
	 * @see PropertiesReader
	 * @see TextSimilarity
	 */
	public IssuesSimilarityCalculator(PropertiesReader propertiesReader, TextSimilarity weedOutStrategy,
			TextSimilarity textsSimilarityStrategy)
	{
		this.propertiesReader = propertiesReader;
		this.weedOutStrategy = weedOutStrategy;
		this.textsSimilarity = textsSimilarityStrategy;
		this.alfa = propertiesReader.getAsDouble(Property.MODEL_MIN_ALPHA);
		init();
	}

	/**
	 * Initializes object used for collecting issue comments.
	 * 
	 * @see IssuesSimilarityCommentsCollector
	 */
	public void init()
	{
		this.issuesSimilarityCommentsCollector = getIssuesSimilarityCommentsCollector();
		this.listPartitioner = new ListPartitioner(propertiesReader.getAsInt(Property.K_RESULTS));
	}

	protected void setListPartitioner(ListPartitioner listPartitioner)
	{
		this.listPartitioner = listPartitioner;
	}

	/**
	 * First variant of similarity calculations. Similarity depends on summary,
	 * description and comments assigned to issue in Jira.
	 * 
	 * @param issue1
	 *            - issue for which we are looking for similarity
	 * @param issue2
	 *            - issue with which we compare
	 * @return similarity between two given issues
	 * @see JiraIssue
	 */
	public double getIssuesSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		return propertiesReader.getAsDouble(Property.SUMMARY_WEIGHT) * calculateSimilarity(issue1.getSummary(), issue2.getSummary())
				+ propertiesReader.getAsDouble(Property.DESCRIPTION_WEIGHT)
						* calculateSimilarity(issue1.getDescription(), issue2.getDescription())
				+ calculateCommentsSimilarity(issue1, issue2);
	}

	/**
	 * Second variant of similarity calculations. Similarity depends on summary
	 * and description assigned to issue in Jira.
	 * 
	 * @param issue1
	 *            - issue for which we are looking for similarity
	 * @param issue2
	 *            - issue with which we compare
	 * @return similarity between two given issues
	 * @see JiraIssue
	 */
	public double getIssuesSimilarityWithoutComments(JiraIssue issue1, JiraIssue issue2)
	{
		return (propertiesReader.getAsDouble(Property.SUMMARY_WEIGHT)
				+ (double) (propertiesReader.getAsDouble(Property.COMMENTS_WEIGHT) / 2))
				* calculateSimilarity(issue1.getSummary(), issue2.getSummary())
				+ (propertiesReader.getAsDouble(Property.DESCRIPTION_WEIGHT)
						+ (double) (propertiesReader.getAsDouble(Property.COMMENTS_WEIGHT) / 2))
						* calculateSimilarity(issue1.getDescription(), issue2.getDescription());
	}

	private double calculateCommentsSimilarity(JiraIssue issue1, JiraIssue issue2)
	{
		if (checkForIssueComments(issue2))
		{
			return propertiesReader.getAsDouble(Property.COMMENTS_WEIGHT)
					* calculateSimilarity(issue1.getSummary(), issuesSimilarityCommentsCollector.collectIssueComments(issue2).toString());
		} else
			return 0.0;
	}

	private boolean checkForAllRequiredTexts(JiraIssue issue1, JiraIssue issue2)
	{
		return ((issue1.getSummary() != null) && (issue1.getDescription() != null) && (issue2.getSummary() != null)
				&& (issue2.getDescription() != null));
	}

	private boolean checkForIssueComments(JiraIssue issue)
	{
		StringBuilder sb = issuesSimilarityCommentsCollector.collectIssueComments(issue);
		return (sb != null) && (sb.toString().trim().length() > 0);
	}

	private double calculateSimilarity(String text1, String text2)
	{
		double preSimilarity = weedOutStrategy.getSimilarity(text1, text2);
		return preSimilarity > propertiesReader.getAsDouble(Property.MODEL_MIN_SIGMA) ? textsSimilarity.getSimilarity(text1, text2)
				: preSimilarity;
	}

	protected IssuesSimilarityCommentsCollector getIssuesSimilarityCommentsCollector()
	{
		return new IssuesSimilarityCommentsCollector();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AssigneeIssueSimilarity getAssigneesWithIssueSimilarities(AssigneeIssues assigneeIssues, JiraIssue newJiraIssue, Date startedAt, Date endedAt)
	{
		List<JiraIssue> assignedJiraIssuesWithStartedDate = getIssuesWithStartedDate(assigneeIssues.getAssignedJiraIssues(), startedAt, endedAt);
		List<JiraIssueSimilarity> jiraIssueSimilarities = new ArrayList<JiraIssueSimilarity>(assignedJiraIssuesWithStartedDate.size());
		for (JiraIssue issue : assignedJiraIssuesWithStartedDate)
		{
			addIssueSimilarity(newJiraIssue, jiraIssueSimilarities, issue);
		}
		return new AssigneeIssueSimilarity(assigneeIssues.getAssignee(), listPartitioner.getList(jiraIssueSimilarities));

	}



	private List<JiraIssue> getIssuesWithStartedDate(List<JiraIssue> assignedJiraIssues, Date startedAt, Date endedAt)
	{
		if(startedAt == null || endedAt == null)
		{
			return assignedJiraIssues;
		}
		List<JiraIssue> jiraIssues = new ArrayList<>();
		for (JiraIssue issue : assignedJiraIssues)
		{
			if (issue.getCreatedAt().after(startedAt) && issue.getCreatedAt().before(endedAt))
			{
				jiraIssues.add(issue);
			}
		}
		return jiraIssues;
	}

	private void addIssueSimilarity(JiraIssue newJiraIssue, List<JiraIssueSimilarity> jiraIssueSimilarities, JiraIssue issue)
	{
		double issueSimilarity = 0.0;
		if (issue.getJiraIssueId() != newJiraIssue.getJiraIssueId() && (checkForAllRequiredTexts(newJiraIssue, issue)))
		{
			if (propertiesReader.getAsBoolean(Property.INCLUDE_COMMENTS_SIMILARITY))
				issueSimilarity = getIssuesSimilarity(newJiraIssue, issue);
			else
				issueSimilarity = getIssuesSimilarityWithoutComments(newJiraIssue, issue);
			if (issueSimilarity >= alfa)
				jiraIssueSimilarities.add(new JiraIssueSimilarity(issue, issueSimilarity));
		}
	}

}
