package similarity;

import java.util.Iterator;

import database.entity.IssueComment;
import database.entity.JiraIssue;

/**
 * Collects all issue comments.
 *
 */
public class IssuesSimilarityCommentsCollector
{
	/**
	 * Returns a StrngBuilder that contains all issue comments.
	 * 
	 * @param issue - issue for which we are collecting comments
	 * @return StringBuilder with all issue comments.
	 */
	public StringBuilder collectIssueComments(JiraIssue issue) {
		StringBuilder sb = new StringBuilder();
		Iterator<IssueComment> iterator = issue.getIssueComments().iterator();
		while (iterator.hasNext())
		{
			IssueComment setElement = iterator.next();
			sb.append(setElement.getContent());
		}
		return sb;
	}
}
