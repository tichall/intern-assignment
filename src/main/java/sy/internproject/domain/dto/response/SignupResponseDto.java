package sy.internproject.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import sy.internproject.domain.entity.User;

import java.util.List;

@Getter
@Builder
public class SignupResponseDto {
    private String username;
    private String nickname;
    private List<AuthorityResponseDto> authorities;

    public static SignupResponseDto of(User user) {
        return SignupResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorities(user.getAuthorities().stream().map(auth -> AuthorityResponseDto.of(auth.getAuthorityEnum())).toList())
                .build();
    }
}
