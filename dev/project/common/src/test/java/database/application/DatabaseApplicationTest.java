package database.application;

import static org.junit.Assert.*;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import database.entity.Assignee;
import database.entity.JiraIssue;
import database.entity.JiraProject;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseApplicationTest
{
	@Mock
	private SessionFactory sessionFactory;
	@Mock
	private Session session;
	@Mock
	private Criteria criteria;
	@Mock
	private JiraProject jiraProject;
	
	private Criterion criterion;
	private Criterion expectation;
	private ArgumentCaptor<Criterion> captor;

	private DatabaseApplication serviceUnderTest;
	
	@Before
	public void setUp() {
		Mockito.reset(sessionFactory, session, criteria);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createCriteria(JiraIssue.class)).thenReturn(criteria);
		Mockito.when(criteria.setFetchMode(Mockito.anyString(), (FetchMode) Mockito.anyObject())).thenReturn(criteria);
		Mockito.when(criteria.setFirstResult(Mockito.anyInt())).thenReturn(criteria);
		Mockito.when(criteria.setMaxResults(Mockito.anyInt())).thenReturn(criteria);
		Mockito.when(criteria.createAlias(Mockito.anyString(), Mockito.anyString())).thenReturn(criteria);
		Mockito.when(criteria.add((Criterion) Mockito.anyObject())).thenReturn(criteria);
		serviceUnderTest = new DatabaseApplication(sessionFactory);
	}
	
	@SuppressWarnings("rawtypes")
	public void changeSetup(Class className) {
		Mockito.reset(criteria);
		Mockito.when(session.createCriteria(className)).thenReturn(criteria);
		Mockito.when(criteria.setFetchMode(Mockito.anyString(), (FetchMode) Mockito.anyObject())).thenReturn(criteria);
		Mockito.when(criteria.setFirstResult(Mockito.anyInt())).thenReturn(criteria);
		Mockito.when(criteria.setMaxResults(Mockito.anyInt())).thenReturn(criteria);
		Mockito.when(criteria.createAlias(Mockito.anyString(), Mockito.anyString())).thenReturn(criteria);
		Mockito.when(criteria.add((Criterion) Mockito.anyObject())).thenReturn(criteria);
	}

	@Test
	public void getJiraIssueTest() {
		captor = ArgumentCaptor.forClass(Criterion.class);
	    serviceUnderTest.getJiraIssue(1);
	    // ensure a call to criteria.add and record the argument the method call had
	    Mockito.verify(criteria).add(captor.capture());
	    criterion = captor.getValue();
	    expectation = Restrictions.eq("id", 1);
	    // toString() because two instances seem never two be equal
	    assertEquals(expectation.toString(), criterion.toString());
	}
	
	@Test
	public void getJiraProjectTest() {
		changeSetup(JiraProject.class);
		captor = ArgumentCaptor.forClass(Criterion.class);
		jiraProject = serviceUnderTest.getJiraProject(1);
	    Mockito.verify(criteria).add(captor.capture());
	    criterion = captor.getValue();
	    expectation = Restrictions.eq("id", 1);
	    assertEquals(expectation.toString(), criterion.toString());
	}
	
	@Test
	public void getJiraIssuesTest() {
		captor = ArgumentCaptor.forClass(Criterion.class);
		serviceUnderTest.getJiraIssues(jiraProject);
	    Mockito.verify(criteria).add(captor.capture());
	    criterion = captor.getValue();
	    expectation = Restrictions.eq("jiraProject", jiraProject);
	    assertEquals(expectation.toString(), criterion.toString());
	}
	
	@Test
	public void getAssigneesTest() {
		changeSetup(Assignee.class);
		captor = ArgumentCaptor.forClass(Criterion.class);
		serviceUnderTest.getJiraIssues(jiraProject);
	    Mockito.verify(criteria).add(captor.capture());
	    criterion = captor.getValue();
	    expectation = Restrictions.eq("jiraProject", jiraProject);
	    assertEquals(expectation.toString(), criterion.toString());
	}

	@Test
	public void getJiraProjectsTest() {
		changeSetup(JiraProject.class);
	    assertNull(serviceUnderTest.getJiraProjects());
	}
	
	@Test
	public void closeSessionTest() {
		serviceUnderTest.closeSession();
		assertFalse(session.isOpen());
	}

}
