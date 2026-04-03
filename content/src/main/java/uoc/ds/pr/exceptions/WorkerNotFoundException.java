package uoc.ds.pr.exceptions;

public class WorkerNotFoundException extends DSException {

    private static final long serialVersionUID = 1L;

    public WorkerNotFoundException() {
        super();
    }

    public WorkerNotFoundException(String msg) {
        super(msg);
    }
}