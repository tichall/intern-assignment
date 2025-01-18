package sy.internproject.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import sy.internproject.domain.entity.User;
import sy.internproject.domain.enums.Authority;

@Getter
@Builder
public class SignupResponseDto {
    private String username;
    private String nickname;
    private Authority authority;

    public static SignupResponseDto of(User user) {
        return SignupResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authority(user.getAuthority())
                .build();
    }
}
