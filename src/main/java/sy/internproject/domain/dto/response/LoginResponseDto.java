package sy.internproject.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    @Schema(description = "발급된 Access Token", example = "BEARER 1sdif98nek~")
    private String token;

    public static LoginResponseDto of(String token) {
        return LoginResponseDto.builder()
                .token(token)
                .build();
    }
}
