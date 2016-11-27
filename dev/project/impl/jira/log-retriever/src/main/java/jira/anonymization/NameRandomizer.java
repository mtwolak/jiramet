package jira.anonymization;

import java.util.List;

import org.kohsuke.randname.RandomNameGenerator;

import database.entity.Assignee;
import database.entity.IssueReporter;
import database.manager.DataBaseType;
import jira.data.IssueDbContext;

public class NameRandomizer
{
	public static void randomizeAllNames(DataBaseType database)
	{
		IssueDbContext idc = new IssueDbContext(database);
		idc.initDbm();
		
		List<Assignee> assignees = idc.getAllAssignees();
		List<IssueReporter> reporters = idc.getAllReporters();

		RandomNameGenerator rnd = new RandomNameGenerator(0);

		for (Assignee assignee : assignees)
		{
			assignee.setName(rnd.next());
		}

		for (IssueReporter reporter : reporters)
		{
			reporter.setFullName(rnd.next());
		}
		
		idc.updateAssignees(assignees);
		idc.updateReporters(reporters);
		
		idc.closeDbm();
	}

}
