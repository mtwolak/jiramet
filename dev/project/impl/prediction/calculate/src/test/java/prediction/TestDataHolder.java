package prediction;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import database.entity.AssignedIssue;
import database.entity.Assignee;
import database.entity.JiraIssue;
import jira.JiraIssueSimilarity;
import utils.DateConverter;

public class TestDataHolder
{
	DateFormat format;

	private Assignee developer1;
	private Assignee developer2;
	private Assignee developer3;
	private Assignee developer4;
	private Assignee developer5;

	private JiraIssue issue1;
	private JiraIssue issue2;
	private JiraIssue issue3;
	private JiraIssue issue4;
	private JiraIssue issue5;
	private JiraIssue issue6;
	private JiraIssue issue7;
	private JiraIssue issue8;

	private AssignedIssue assignedIssue1;
	private AssignedIssue assignedIssue2;
	private AssignedIssue assignedIssue3;
	private AssignedIssue assignedIssue4;
	private AssignedIssue assignedIssue5;
	private AssignedIssue assignedIssue6;
	private AssignedIssue assignedIssue7;
	private AssignedIssue assignedIssue8;
	
	private JiraIssueSimilarity similarity1;
	private JiraIssueSimilarity similarity2;
	private JiraIssueSimilarity similarity3;
	private JiraIssueSimilarity similarity4;
	private JiraIssueSimilarity similarity5;
	private JiraIssueSimilarity similarity6;
	private JiraIssueSimilarity similarity7;
	private JiraIssueSimilarity similarity8;
	
	private AssigneeTopSimilarities developer1TopSimilarities;
	private AssigneeTopSimilarities developer2TopSimilarities;
	private AssigneeTopSimilarities developer3TopSimilarities;
	private AssigneeTopSimilarities developer5TopSimilarities;
	
	private List<JiraIssueSimilarity> issueSimilarities;

	public TestDataHolder()
	{
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		issueSimilarities = new ArrayList<JiraIssueSimilarity>();
		initDevelopers();
		initIssues();
		initAssignedIssues();
		initSimilarities();
		initTopSimilarities();
	}

	private void initTopSimilarities()
	{
		developer1TopSimilarities = new AssigneeTopSimilarities(5);
		developer2TopSimilarities = new AssigneeTopSimilarities(5);
		developer3TopSimilarities = new AssigneeTopSimilarities(5);
		developer5TopSimilarities = new AssigneeTopSimilarities(5);
		
		developer1TopSimilarities.setAssignee(developer1);
		developer1TopSimilarities.addJiraIssueSimilarity(similarity1);
		developer1TopSimilarities.addJiraIssueSimilarity(similarity2);
		
		developer2TopSimilarities.setAssignee(developer2);
		developer2TopSimilarities.addJiraIssueSimilarity(similarity3);
		developer2TopSimilarities.addJiraIssueSimilarity(similarity4);
		developer2TopSimilarities.addJiraIssueSimilarity(similarity5);
		
		developer3TopSimilarities.setAssignee(developer3);
		developer3TopSimilarities.addJiraIssueSimilarity(similarity6);
		
		developer5TopSimilarities.setAssignee(developer5);
		developer5TopSimilarities.addJiraIssueSimilarity(similarity7);
		developer5TopSimilarities.addJiraIssueSimilarity(similarity8);
		
	}

	private void initSimilarities()
	{
		similarity1 = new JiraIssueSimilarity(issue1, 0.012);
		similarity2 = new JiraIssueSimilarity(issue2, 0.24);
		similarity3 = new JiraIssueSimilarity(issue3, 0.43);
		similarity4 = new JiraIssueSimilarity(issue4, 0.039);
		similarity5 = new JiraIssueSimilarity(issue5, 0.0098);
		similarity6 = new JiraIssueSimilarity(issue6, 0.0422);
		similarity7 = new JiraIssueSimilarity(issue7, 0.0098);
		similarity8 = new JiraIssueSimilarity(issue8, 0.0029);
		
		issueSimilarities.add(similarity1);
		issueSimilarities.add(similarity2);
		issueSimilarities.add(similarity3);
		issueSimilarities.add(similarity4);
		issueSimilarities.add(similarity5);
		issueSimilarities.add(similarity6);
		issueSimilarities.add(similarity7);
		issueSimilarities.add(similarity8);
		
	}

	private void initAssignedIssues()
	{
		Timestamp resolveDate = DateConverter.convertStringToTimestamp("2016-11-28 21:00", format);

		assignedIssue1 = new AssignedIssue();
		assignedIssue1.setAssignee(developer1);
		assignedIssue1.setJiraIssue(issue1);
		assignedIssue1.setResolvedAt(resolveDate);
		assignedIssue2 = new AssignedIssue();
		assignedIssue2.setAssignee(developer1);
		assignedIssue2.setJiraIssue(issue2);
		assignedIssue2.setResolvedAt(resolveDate);
		assignedIssue3 = new AssignedIssue();
		assignedIssue3.setAssignee(developer2);
		assignedIssue3.setJiraIssue(issue3);
		assignedIssue3.setResolvedAt(resolveDate);
		assignedIssue4 = new AssignedIssue();
		assignedIssue4.setAssignee(developer2);
		assignedIssue4.setJiraIssue(issue4);
		assignedIssue4.setResolvedAt(resolveDate);
		assignedIssue5 = new AssignedIssue();
		assignedIssue5.setAssignee(developer2);
		assignedIssue5.setJiraIssue(issue5);
		assignedIssue5.setResolvedAt(resolveDate);
		assignedIssue6 = new AssignedIssue();
		assignedIssue6.setAssignee(developer3);
		assignedIssue6.setJiraIssue(issue6);
		assignedIssue6.setResolvedAt(resolveDate);
		assignedIssue7 = new AssignedIssue();
		assignedIssue7.setAssignee(developer5);
		assignedIssue7.setJiraIssue(issue7);
		assignedIssue7.setResolvedAt(resolveDate);
		assignedIssue8 = new AssignedIssue();
		assignedIssue8.setAssignee(developer5);
		assignedIssue8.setJiraIssue(issue8);
		assignedIssue8.setResolvedAt(resolveDate);
		
		addConnectionToIssues();

	}

	private void addConnectionToIssues()
	{
		Set<AssignedIssue> set1 = new HashSet<AssignedIssue>();
		set1.add(assignedIssue1);
		Set<AssignedIssue> set2 = new HashSet<AssignedIssue>();
		set2.add(assignedIssue2);
		Set<AssignedIssue> set3 = new HashSet<AssignedIssue>();
		set3.add(assignedIssue3);
		Set<AssignedIssue> set4 = new HashSet<AssignedIssue>();
		set4.add(assignedIssue4);
		Set<AssignedIssue> set5 = new HashSet<AssignedIssue>();
		set5.add(assignedIssue5);
		Set<AssignedIssue> set6 = new HashSet<AssignedIssue>();
		set6.add(assignedIssue6);
		Set<AssignedIssue> set7 = new HashSet<AssignedIssue>();
		set7.add(assignedIssue7);
		Set<AssignedIssue> set8 = new HashSet<AssignedIssue>();
		set8.add(assignedIssue8);
		
		issue1.setAssignedIssues(set1);
		issue2.setAssignedIssues(set2);
		issue3.setAssignedIssues(set3);
		issue4.setAssignedIssues(set4);
		issue5.setAssignedIssues(set5);
		issue6.setAssignedIssues(set6);
		issue7.setAssignedIssues(set7);
		issue8.setAssignedIssues(set8);
	}

	private void initIssues()
	{
		issue1 = new JiraIssue();
		issue2 = new JiraIssue();
		issue3 = new JiraIssue();
		issue4 = new JiraIssue();
		issue5 = new JiraIssue();
		issue6 = new JiraIssue();
		issue7 = new JiraIssue();
		issue8 = new JiraIssue();

		issue1.setCreatedAt(DateConverter.convertStringToTimestamp("2016-11-26 21:00", format));
		issue2.setCreatedAt(DateConverter.convertStringToTimestamp("2016-11-24 21:00", format));
		issue3.setCreatedAt(DateConverter.convertStringToTimestamp("2016-11-25 21:00", format));
		issue4.setCreatedAt(DateConverter.convertStringToTimestamp("2016-11-21 21:00", format));
		issue5.setCreatedAt(DateConverter.convertStringToTimestamp("2016-11-21 21:00", format));
		issue6.setCreatedAt(DateConverter.convertStringToTimestamp("2016-11-26 21:00", format));
		issue7.setCreatedAt(DateConverter.convertStringToTimestamp("2016-11-26 21:00", format));
		issue8.setCreatedAt(DateConverter.convertStringToTimestamp("2016-11-26 21:00", format));
	}

	private void initDevelopers()
	{
		developer1 = new Assignee();
		developer2 = new Assignee();
		developer3 = new Assignee();
		developer4 = new Assignee();
		developer5 = new Assignee();

		developer1.setName("Michal");
		developer2.setName("Tomek");
		developer3.setName("Marcin");
		developer4.setName("Mathew");
		developer5.setName("Bartek");
	}
	
	public List<JiraIssueSimilarity> getIssueSimilarities()
	{
		return issueSimilarities;
	}
	
	public Assignee getDeveloper1()
	{
		return developer1;
	}
	
	public Assignee getDeveloper4()
	{
		return developer4;
	}
	
	public AssigneeTopSimilarities getDeveloper1TopSimilarities()
	{
		return developer1TopSimilarities;
	}
	
	public AssigneeTopSimilarities getDeveloper2TopSimilarities()
	{
		return developer2TopSimilarities;
	}
	
	public AssigneeTopSimilarities getDeveloper3TopSimilarities()
	{
		return developer3TopSimilarities;
	}
	
	public AssigneeTopSimilarities getDeveloper5TopSimilarities()
	{
		return developer5TopSimilarities;
	}

}
