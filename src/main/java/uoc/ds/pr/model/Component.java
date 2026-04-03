package uoc.ds.pr.model;

public class Component {

    private final String id;
    private String trademark;
    private String model;
    private String serial;

    private System system;
    private Issue[] issues;
    private int numIssues;

    public Component(String id, String trademark, String model, String serial) {
        this.id = id;
        this.trademark = trademark;
        this.model = model;
        this.serial = serial;
        this.issues = new Issue[4];
        this.numIssues = 0;
    }

    public String getId() {
        return id;
    }

    public String getTrademark() {
        return trademark;
    }

    public String getModel() {
        return model;
    }

    public String getSerial() {
        return serial;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public void addIssue(Issue issue) {
        ensureIssuesCapacity();
        issues[numIssues++] = issue;
    }

    public int numIssues() {
        return numIssues;
    }

    public Issue getIssue(int index) {
        return issues[index];
    }

    private void ensureIssuesCapacity() {
        if (numIssues == issues.length) {
            Issue[] newArray = new Issue[issues.length * 2];
            for (int i = 0; i < issues.length; i++) {
                newArray[i] = issues[i];
            }
            issues = newArray;
        }
    }
}