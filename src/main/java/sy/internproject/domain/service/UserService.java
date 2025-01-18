package sy.internproject.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sy.internproject.domain.dto.request.SignupRequestDto;
import sy.internproject.domain.dto.response.SignupResponseDto;
import sy.internproject.domain.entity.User;
import sy.internproject.domain.repository.UserRepository;
import sy.internproject.global.exception.CustomException;
import sy.internproject.global.exception.errorCode.UserErrorCode;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        validateUsernameUniqueness(requestDto.getUsername());

        User user = User.createBasicUser(requestDto, passwordEncoder);
        userRepository.save(user);
        return SignupResponseDto.of(user);
    }

    private void validateUsernameUniqueness(String username) {
        if(userRepository.existsByUsername(username)) {
            throw new CustomException(UserErrorCode.DUPLICATED_USER);
        }
    }
}
