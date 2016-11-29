package jira.anonymization;

import java.util.List;

import org.kohsuke.randname.RandomNameGenerator;

import database.entity.Assignee;
import database.entity.IssueReporter;
import jira.data.IssueDbContext;
import utils.properties.hibernate.HibernateConfiguration;

public class NameRandomizer
{
	public static void randomizeAllNames(HibernateConfiguration hibernateConfiguration)
	{
		IssueDbContext idc = new IssueDbContext(hibernateConfiguration);
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
		
	}

}
