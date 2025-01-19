package sy.internproject.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import sy.internproject.domain.enums.AuthorityEnum;

@Getter
@Builder
public class AuthorityResponseDto {
    private AuthorityEnum authorityName;

    public static AuthorityResponseDto of(AuthorityEnum authorityName) {
        return AuthorityResponseDto.builder()
                .authorityName(authorityName)
                .build();
    }
}
