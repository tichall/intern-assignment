package sy.internproject.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDto {
    @Schema(description = "유저 이름", example = "Tichall")
    @NotBlank
    private String username;

    @Schema(description = "비밀번호")
    @NotBlank
    private String password;

    @Schema(description = "별명", example = "하이유")
    @NotBlank
    private String nickname;
}
