package backtraceio.library.models.types;

/* loaded from: classes.dex */
public class HttpException extends Exception {
    private Integer httpStatus;

    public HttpException(String message) {
        this(null, message);
    }

    public HttpException(Integer httpStatus) {
        this(httpStatus, null);
    }

    public HttpException(Integer httpStatus, String message) {
        super(message);
        setHttpStatus(httpStatus.intValue());
    }

    public int getHttpStatus() {
        return this.httpStatus.intValue();
    }

    private void setHttpStatus(int httpStatus) {
        this.httpStatus = Integer.valueOf(httpStatus);
    }
}
