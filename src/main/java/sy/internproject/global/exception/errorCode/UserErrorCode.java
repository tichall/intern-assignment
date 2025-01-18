package sy.internproject.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    DUPLICATED_USER(404, "이미 가입된 회원입니다.")
    ;

    private final int statusCode;
    private final String message;
}