package sy.internproject.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(404, "회원을 찾을 수 없습니다."),
    DUPLICATED_USER(404, "이미 가입된 회원입니다."),
    NOT_FOUND_AUTHORITY(401, "해당 권한을 찾을 수 없습니다.")
    ;

    private final int statusCode;
    private final String message;
}