package sy.internproject.global.exception;

import lombok.Getter;
import sy.internproject.global.exception.errorCode.ErrorCode;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
