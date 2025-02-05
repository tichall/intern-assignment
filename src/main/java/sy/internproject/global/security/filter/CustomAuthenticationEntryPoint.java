package sy.internproject.global.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import sy.internproject.global.exception.CustomSecurityException;
import sy.internproject.global.exception.errorCode.CommonErrorCode;
import sy.internproject.global.utils.ResponseUtil;

import java.io.IOException;

@Slf4j(topic = "인증/인가 예외 처리")
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Exception exception = (Exception) request.getAttribute("exception");

        if (exception instanceof CustomSecurityException e) {
            ResponseUtil.writeJsonErrorResponse(response, e.getErrorCode());
            return;
        }

        ResponseUtil.writeJsonErrorResponse(response, CommonErrorCode.INTERNAL_SERVER_ERROR);
    }
}
