package uoc.ds.pr.exceptions;

public class IssueAlreadyResolvedException extends DSException {

    private static final long serialVersionUID = 1L;

    public IssueAlreadyResolvedException() {
        super();
    }

    public IssueAlreadyResolvedException(String msg) {
        super(msg);
    }
}