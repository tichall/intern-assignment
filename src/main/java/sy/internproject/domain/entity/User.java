package sy.internproject.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import sy.internproject.domain.dto.request.SignupRequestDto;
import sy.internproject.domain.enums.Authority;
import sy.internproject.domain.enums.OAuthProvider;
import sy.internproject.domain.enums.UserStatus;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public static User createBasicUser(SignupRequestDto requestDto, PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(requestDto.getNickname())
                .oAuthProvider(OAuthProvider.ORIGIN)
                .authority(Authority.ROLE_USER)
                .userStatus(UserStatus.ACTIVATE)
                .build();
    }
}