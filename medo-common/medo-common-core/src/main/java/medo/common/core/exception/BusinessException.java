package medo.common.core.exception;

/**
 * 
 * @author: bryce
 * @date: 2020-08-04
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 6610083281801529147L;

    public BusinessException(String message) {
        super(message);
    }
}
