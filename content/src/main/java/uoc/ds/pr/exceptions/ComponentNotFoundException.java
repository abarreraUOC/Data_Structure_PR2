package uoc.ds.pr.exceptions;

public class ComponentNotFoundException extends DSException {
    private static final long serialVersionUID = 1L;

    public ComponentNotFoundException() {
        super();
    }

    public ComponentNotFoundException(String msg) {
        super(msg);
    }
}