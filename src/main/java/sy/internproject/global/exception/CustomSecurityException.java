package sy.internproject.global.exception;

import sy.internproject.global.exception.errorCode.ErrorCode;

public class CustomSecurityException extends CustomException {
    public CustomSecurityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
