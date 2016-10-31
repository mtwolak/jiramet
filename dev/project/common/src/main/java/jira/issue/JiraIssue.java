package jira.issue;

import java.util.Date;
import java.util.List;

import jira.JiraComment;

public class JiraIssue {
	private String author;
	private Date creation;
	private Date resolved;
	private Date firstResponse;
	private List<JiraComment> comments;

}
