package summer.exception;

/**
 * parse body exception
 *
 * Created by xuan on 2018/4/26
 */
public class BodyException extends RuntimeException {

    public BodyException() {
    }

    public BodyException(String message) {
        super(message);
    }

    public BodyException(String message, Throwable cause) {
        super(message, cause);
    }
}
