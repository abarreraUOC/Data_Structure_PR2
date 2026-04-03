package uoc.ds.pr.exceptions;

public class IssueNotFoundException extends DSException {

    private static final long serialVersionUID = 1L;

    public IssueNotFoundException() {
        super();
    }

    public IssueNotFoundException(String msg) {
        super(msg);
    }
}