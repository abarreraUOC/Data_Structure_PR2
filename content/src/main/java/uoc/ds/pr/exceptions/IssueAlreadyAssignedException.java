package uoc.ds.pr.exceptions;

public class IssueAlreadyAssignedException extends DSException {

    private static final long serialVersionUID = 1L;

    public IssueAlreadyAssignedException() {
        super();
    }

    public IssueAlreadyAssignedException(String msg) {
        super(msg);
    }
}