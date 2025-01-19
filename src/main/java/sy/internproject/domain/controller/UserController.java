package sy.internproject.domain.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

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
