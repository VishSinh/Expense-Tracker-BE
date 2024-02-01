package live.vishsinh.expensetracker.helpers;

import org.springframework.http.HttpStatus;

public class ResponseObj {
    public Boolean success;
    public String message;
    public Object data;
    public HttpStatus status;

    public ResponseObj(Boolean success, String message, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.data = null;
        this.status = status;
    }

    public ResponseObj(Boolean success, String message, Object data, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.status = status;
    }
}
