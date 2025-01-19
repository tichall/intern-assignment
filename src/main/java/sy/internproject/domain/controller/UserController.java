package sy.internproject.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sy.internproject.domain.dto.request.SignupRequestDto;
import sy.internproject.domain.dto.response.SignupResponseDto;
import sy.internproject.domain.service.UserService;

@Tag(name = "유저", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원 가입", description = "회원 정보를 받아 회원 가입을 진행합니다.")
    @ApiResponse(responseCode = "201", description = "회원 가입 성공")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signupForBasicUser(
            @RequestBody @Valid SignupRequestDto requestDto
    ) {
        SignupResponseDto responseDto = userService.signupForBasicUser(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }
}
