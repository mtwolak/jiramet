package similarity;

import java.util.Iterator;

import database.entity.IssueComment;
import database.entity.JiraIssue;

public class IssuesSimilarityHelper
{
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
