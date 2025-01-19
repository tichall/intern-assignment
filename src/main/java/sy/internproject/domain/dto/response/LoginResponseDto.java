package sy.internproject.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    private String token;

    public static LoginResponseDto of(String token) {
        return LoginResponseDto.builder()
                .token(token)
                .build();
    }
}
