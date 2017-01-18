package jira.anonymization;

import java.util.List;

import org.kohsuke.randname.RandomNameGenerator;

import database.entity.Assignee;
import database.entity.IssueComment;
import database.entity.IssueReporter;
import jira.data.IssueDbContext;
import utils.properties.hibernate.HibernateConfiguration;

/**
 * Contains method responsible for data anonymization. The method works directly
 * on the database, changing all assignee's and issue reporter's names
 *
 */
public class NameRandomizer {

	/**
	 * Changes all original user names downloaded from JIRA project to randomly
	 * generated ones, which provides the data anonymization
	 * 
	 * @param hibernateConfiguration
	 *            database properties
	 * @see HibernateConfiguration
	 */
	public static void randomizeAllNames(HibernateConfiguration hibernateConfiguration) {
		IssueDbContext idc = new IssueDbContext(hibernateConfiguration);
		idc.initDbm();

		List<Assignee> assignees = idc.getAllAssignees();
		List<IssueReporter> reporters = idc.getAllReporters();

		RandomNameGenerator rnd = new RandomNameGenerator(0);

		for (Assignee assignee : assignees) {
			if (!"Unassinged".equals(assignee.getName())) {
				String randomName = rnd.next();
				updateComments(idc, assignee, randomName);
				assignee.setName(randomName);
			}
		}

		for (IssueReporter reporter : reporters) {
			reporter.setFullName(rnd.next());
		}

		idc.updateAssignees(assignees);
		idc.updateReporters(reporters);

	}

	@SuppressWarnings("unchecked")
	private static void updateComments(IssueDbContext idc, Assignee assignee, String generatedName) {

		List<IssueComment> assigneesComments = idc.getAssigneesComments(assignee);

		for (IssueComment comment : assigneesComments) {
			comment.setAddedBy(generatedName);
		}
		
		idc.updateComments(assigneesComments);
	}

}
