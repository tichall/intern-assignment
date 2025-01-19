package sy.internproject.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sy.internproject.domain.dto.request.SignupRequestDto;
import sy.internproject.domain.dto.response.SignupResponseDto;
import sy.internproject.domain.entity.Authority;
import sy.internproject.domain.entity.User;
import sy.internproject.domain.enums.AuthorityEnum;
import sy.internproject.domain.repository.AuthorityRepository;
import sy.internproject.domain.repository.UserRepository;
import sy.internproject.global.exception.CustomException;
import sy.internproject.global.exception.errorCode.UserErrorCode;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponseDto signupForBasicUser(SignupRequestDto requestDto) {
        validateUsernameUniqueness(requestDto.getUsername());

        User user = User.toEntity(
                requestDto.getUsername(),
                passwordEncoder.encode(requestDto.getPassword()),
                requestDto.getNickname(),
                new ArrayList<>()
        );

        Authority authority = authorityRepository.findByAuthorityEnum(AuthorityEnum.ROLE_USER)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND_AUTHORITY));

        user.addAuthority(authority);
        userRepository.save(user);
        return SignupResponseDto.of(user);
    }

    private void validateUsernameUniqueness(String username) {
        if(userRepository.existsByUsername(username)) {
            throw new CustomException(UserErrorCode.DUPLICATED_USER);
        }
    }
}
