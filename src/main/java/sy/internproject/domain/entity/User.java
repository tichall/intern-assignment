package sy.internproject.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import sy.internproject.domain.enums.OAuthProvider;
import sy.internproject.domain.enums.UserStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAuthority> userAuthorities = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public static User toEntity(String username, String password, String nickname, List<UserAuthority> userAuthorities) {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .userAuthorities(userAuthorities)
                .oAuthProvider(OAuthProvider.ORIGIN)
                .userStatus(UserStatus.ACTIVATE)
                .build();
    }

    public void addAuthority(Authority authority) {
        UserAuthority userAuthority = UserAuthority.of(this, authority);
        this.userAuthorities.add(userAuthority);
    }

    public List<Authority> getAuthorities() {
        return this.userAuthorities.stream().map(UserAuthority::getAuthority).collect(Collectors.toList());
    }
}