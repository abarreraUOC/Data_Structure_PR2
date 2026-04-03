package uoc.ds.pr.model;

import uoc.ds.pr.util.StackLinkedList;

public class Worker {

    private final String id;
    private String name;
    private String address;

    private final StackLinkedList<Issue> assignedIssues;
    private Issue[] doneIssues;
    private int numDoneIssues;

    public Worker(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.assignedIssues = new StackLinkedList<>();
        this.doneIssues = new Issue[4];
        this.numDoneIssues = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void assignIssue(Issue issue) {
        assignedIssues.push(issue);
    }

    public boolean hasAssignedIssues() {
        return !assignedIssues.isEmpty();
    }

    public int numAssignedIssues() {
        return assignedIssues.size();
    }

    public Issue solveLastAssignedIssue() {
        return assignedIssues.pop();
    }

    public void addDoneIssue(Issue issue) {
        ensureDoneIssuesCapacity();
        doneIssues[numDoneIssues++] = issue;
    }

    public int numDoneIssues() {
        return numDoneIssues;
    }

    public Issue getDoneIssue(int index) {
        return doneIssues[index];
    }

    public Issue[] getDoneIssues() {
        return doneIssues;
    }

    private void ensureDoneIssuesCapacity() {
        if (numDoneIssues == doneIssues.length) {
            Issue[] newArray = new Issue[doneIssues.length * 2];
            for (int i = 0; i < doneIssues.length; i++) {
                newArray[i] = doneIssues[i];
            }
            doneIssues = newArray;
        }
    }
}