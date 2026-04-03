package uoc.ds.pr.exceptions;

public class ComponentAlreadyInstalledException extends DSException {

    private static final long serialVersionUID = 1L;

    public ComponentAlreadyInstalledException() {
        super();
    }

    public ComponentAlreadyInstalledException(String msg) {
        super(msg);
    }
}