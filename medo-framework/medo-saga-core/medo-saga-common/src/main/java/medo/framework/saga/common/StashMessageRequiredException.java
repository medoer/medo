package medo.framework.saga.common;

public class StashMessageRequiredException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String target;

    public String getTarget() {
        return target;
    }

    public StashMessageRequiredException(String target) {
        this.target = target;
    }
}
