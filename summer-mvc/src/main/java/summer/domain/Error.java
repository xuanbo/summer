package summer.domain;

import java.io.Serializable;

/**
 * 错误信息
 *
 * 设计参考spring mvc的返回格式
 *
 * Created by xuan on 2018/4/26
 */
public class Error implements Serializable {

    private int status;
    private String message;
    private String exception;
    private String path;
    private long timestamp;

    public Error() {
    }

    public Error(int status, String message, String exception, String path) {
        this(status, message, exception, path, System.currentTimeMillis());
    }

    public Error(int status, String message, String exception, String path, long timestamp) {
        this.status = status;
        this.message = message;
        this.exception = exception;
        this.path = path;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Error{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", exception='" + exception + '\'' +
                ", path='" + path + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
