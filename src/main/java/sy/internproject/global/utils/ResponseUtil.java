package sy.internproject.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import sy.internproject.global.exception.errorCode.ErrorCode;
import sy.internproject.global.response.ErrorResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResponseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void writeJsonResponse(HttpServletResponse response, HttpStatus status, Object data) throws IOException {
        String jsonResponse = objectMapper.writeValueAsString(data);
        response.setStatus(status.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(jsonResponse);
    }

    public static void writeJsonErrorResponse(HttpServletResponse response, ErrorCode errorCode)
            throws IOException {
        ErrorResponse<Void> responseMessage = new ErrorResponse<>(errorCode);

        String jsonResponse = objectMapper.writeValueAsString(responseMessage);

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(
                MediaType.APPLICATION_JSON_VALUE + "; charset=" + StandardCharsets.UTF_8.name());
        response.setStatus(errorCode.getStatusCode());
        response.getWriter().write(jsonResponse);
    }
}
