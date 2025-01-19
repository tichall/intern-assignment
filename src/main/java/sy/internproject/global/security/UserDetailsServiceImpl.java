package sy.internproject.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sy.internproject.domain.entity.Authority;
import sy.internproject.domain.entity.User;
import sy.internproject.domain.repository.UserRepository;
import sy.internproject.global.exception.CustomSecurityException;
import sy.internproject.global.exception.errorCode.UserErrorCode;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username).orElseThrow(() ->
                new CustomSecurityException(UserErrorCode.USER_NOT_FOUND));

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for (Authority authority : user.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(authority.toString()));
        }

        return new UserDetailsImpl(user, authorities);
    }
}