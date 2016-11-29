package jira.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import database.entity.IssueComment;
import database.entity.JiraIssue;
import jira.project.ProjectData;
import utils.DateConverter;

public class CommentDownloader
{

	public static Collection<IssueComment> loadCommentsFromIssue(JiraIssue issue, ProjectData project)
	{
		StringBuilder url = new StringBuilder();
		JsonObject json = null;
		
		url.append(project.getProjectURL());

		if (url.length() == 0)
		{
			return null;
		}

		url.append("/rest/api/2/issue/");
		url.append(issue.getCode());
		url.append("/comment");

		try
		{
			json = readJsonFromUrl(url.toString());
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return getCommentsFromJson(json, issue);
	}

	private static Collection<IssueComment> getCommentsFromJson(JsonObject json, JiraIssue issue)
	{
		if (json == null)
		{
			return null;
		}

		Collection<IssueComment> result = new ArrayList<IssueComment>();

		JsonArray comments;
		comments = json.getAsJsonArray("comments");

		for (int i = 0; i < comments.size(); i++)
		{

			JsonObject comment = comments.get(i).getAsJsonObject();
			IssueComment issueComment = new IssueComment();
			issueComment.setContent(comment.get("body").getAsString());

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
			Timestamp addedAt = DateConverter.convertStringToTimestamp(comment.get("created").getAsString(), format);
			issueComment.setAddedAt(addedAt);

			JsonObject author = comment.get("author").getAsJsonObject();
			if (author != null)
			{
				issueComment.setAddedBy(author.get("displayName").getAsString());
			}

			issueComment.setJiraIssue(issue);
			result.add(issueComment);
		}

		return result;
	}

	/* JSON utility method */
	private static JsonObject readJsonFromUrl(String url) throws IOException
	{
		InputStream is = new URL(url).openStream();
		try
		{
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JsonElement jelement = new JsonParser().parse(jsonText);
			JsonObject jobject = jelement.getAsJsonObject();
			return jobject;
		} finally
		{
			is.close();
		}
	}

	private static String readAll(Reader rd) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1)
		{
			sb.append((char) cp);
		}
		return sb.toString();
	}

}
