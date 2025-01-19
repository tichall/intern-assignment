package sy.internproject.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {
    INVALID_ACCESS_TOKEN(400, "해당 토큰으로 로그인이 불가능합니다."),
    EXPIRED_TOKEN(401, "만료된 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(),"해당 사용자의 리프레쉬 토큰이 존재하지 않습니다, "),
    REFRESH_TOKEN_MISMATCH(HttpStatus.BAD_REQUEST.value(), "리프레시 토큰이 일치하지 않습니다."),
    ;

    private final int statusCode;
    private final String message;
}
