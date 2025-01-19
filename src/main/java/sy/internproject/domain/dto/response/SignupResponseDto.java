package sy.internproject.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import sy.internproject.domain.entity.User;

import java.util.List;

@Getter
@Builder
public class SignupResponseDto {
    @Schema(description = "유저 이름", example = "Tichall")
    private String username;

    @Schema(description = "별명", example = "하이유")
    private String nickname;

    @Schema(description = "권한 종류")
    private List<AuthorityResponseDto> authorities;

    public static SignupResponseDto of(User user) {
        return SignupResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorities(user.getAuthorities().stream().map(auth -> AuthorityResponseDto.of(auth.getAuthorityEnum())).toList())
                .build();
    }
}
