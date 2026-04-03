package uoc.ds.pr;

import edu.uoc.ds.traversal.Iterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.Component;
import uoc.ds.pr.model.Issue;
import uoc.ds.pr.model.System;
import uoc.ds.pr.model.Worker;
import uoc.ds.pr.pr2.SystemIssues;
import uoc.ds.pr.pr2.SystemIssuesHelper;
import uoc.ds.pr.util.CSVUtil;
import uoc.ds.pr.util.DateUtils;

public class SystemIssuesExtraTest {

    private SystemIssues systemIssues;
    private SystemIssuesHelper helper;

    @Before
    public void setUp() throws Exception {
        this.systemIssues = FactorySystemIssues.getComputerProjects();
        this.helper = this.systemIssues.getSystemIssuesHelper();
    }

    @Test
    public void createIssueShouldLinkIssueToComponentTest() throws DSException {
        CSVUtil.addComponents(systemIssues);

        Issue issue = systemIssues.createIssue(
                "ISS-900",
                "COMP01",
                "Test issue",
                DateUtils.createDateTime("2026-03-17T10:00:00")
        );

        Assert.assertEquals("ISS-900", issue.getId());
        Assert.assertEquals("COMP01", issue.getComponent().getId());
        Assert.assertFalse(issue.isResolved());
        Assert.assertEquals(1, helper.numIssues());
        Assert.assertEquals(1, helper.numIssuesByComponent("COMP01"));
    }

    @Test
    public void installComponentShouldUpdateComponentSystemReferenceTest() throws DSException {
        CSVUtil.addSystems(systemIssues);
        CSVUtil.addComponents(systemIssues);

        systemIssues.installComponentToSystem("COMP01", "SYS01");

        Component component = helper.getComponent("COMP01");
        Assert.assertNotNull(component.getSystem());
        Assert.assertEquals("SYS01", component.getSystem().getId());
    }

    @Test
    public void solveIssueShouldRespectLifoWithManualAssignmentsTest() throws DSException {
        CSVUtil.addWorkers(systemIssues);
        CSVUtil.addComponents(systemIssues);

        systemIssues.createIssue("ISS-A", "COMP01", "Issue A",
                DateUtils.createDateTime("2026-03-17T10:00:00"));
        systemIssues.createIssue("ISS-B", "COMP01", "Issue B",
                DateUtils.createDateTime("2026-03-17T11:00:00"));
        systemIssues.createIssue("ISS-C", "COMP01", "Issue C",
                DateUtils.createDateTime("2026-03-17T12:00:00"));

        systemIssues.assignIssue("ISS-A", "WKR3");
        systemIssues.assignIssue("ISS-B", "WKR3");
        systemIssues.assignIssue("ISS-C", "WKR3");

        Issue solved = systemIssues.solveIssue("WKR3");
        Assert.assertEquals("ISS-C", solved.getId());

        solved = systemIssues.solveIssue("WKR3");
        Assert.assertEquals("ISS-B", solved.getId());

        solved = systemIssues.solveIssue("WKR3");
        Assert.assertEquals("ISS-A", solved.getId());
    }

    @Test
    public void solvedIssueCannotBeAssignedAgainTest() throws DSException {
        CSVUtil.addWorkers(systemIssues);
        CSVUtil.addComponents(systemIssues);

        systemIssues.createIssue("ISS-901", "COMP01", "Issue reassign",
                DateUtils.createDateTime("2026-03-17T10:00:00"));
        systemIssues.assignIssue("ISS-901", "WKR3");
        systemIssues.solveIssue("WKR3");

        Assert.assertThrows(IssueAlreadyResolvedException.class, () ->
                systemIssues.assignIssue("ISS-901", "WKR4"));
    }

    @Test
    public void updateWorkerShouldNotDuplicateWorkerTest() throws DSException {
        systemIssues.addWorker("WKR99", "Name 1", "Address 1");
        Assert.assertEquals(1, helper.numWorkers());

        systemIssues.addWorker("WKR99", "Name 2", "Address 2");
        Assert.assertEquals(1, helper.numWorkers());

        Worker worker = helper.getWorker("WKR99");
        Assert.assertEquals("Name 2", worker.getName());
        Assert.assertEquals("Address 2", worker.getAddress());
    }

    @Test
    public void getDoneIssuesShouldKeepResolutionOrderTest() throws DSException {
        CSVUtil.addWorkers(systemIssues);
        CSVUtil.addComponents(systemIssues);

        systemIssues.createIssue("ISS-910", "COMP01", "Issue 1",
                DateUtils.createDateTime("2026-03-17T10:00:00"));
        systemIssues.createIssue("ISS-911", "COMP01", "Issue 2",
                DateUtils.createDateTime("2026-03-17T11:00:00"));

        systemIssues.assignIssue("ISS-910", "WKR3");
        systemIssues.assignIssue("ISS-911", "WKR3");

        systemIssues.solveIssue("WKR3");
        systemIssues.solveIssue("WKR3");

        Iterator<Issue> it = systemIssues.getDoneIssuesByWorker("WKR3");

        Assert.assertTrue(it.hasNext());
        Assert.assertEquals("ISS-911", it.next().getId());

        Assert.assertTrue(it.hasNext());
        Assert.assertEquals("ISS-910", it.next().getId());

        Assert.assertFalse(it.hasNext());
    }

    @Test
    public void getSystemWithMostComponentsShouldChangeAfterMoreInstallationsTest() throws DSException {
        CSVUtil.addSystems(systemIssues);
        CSVUtil.addComponents(systemIssues);

        systemIssues.installComponentToSystem("COMP01", "SYS01");
        systemIssues.installComponentToSystem("COMP02", "SYS01");
        systemIssues.installComponentToSystem("COMP03", "SYS02");

        System system = systemIssues.getSystemWithMostComponents();
        Assert.assertEquals("SYS01", system.getId());

        systemIssues.installComponentToSystem("COMP04", "SYS02");
        systemIssues.installComponentToSystem("COMP05", "SYS02");

        system = systemIssues.getSystemWithMostComponents();
        Assert.assertEquals("SYS02", system.getId());
    }
}