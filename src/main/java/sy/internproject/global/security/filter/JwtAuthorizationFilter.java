package sy.internproject.global.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sy.internproject.global.exception.CustomSecurityException;
import sy.internproject.global.exception.errorCode.TokenErrorCode;
import sy.internproject.global.jwt.JwtHelper;
import sy.internproject.global.jwt.RefreshTokenService;
import sy.internproject.global.security.UserDetailsServiceImpl;

import java.io.IOException;

@Slf4j(topic = "JWT 기반 인가 필터")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtHelper jwtHelper;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtHelper.getJwtFromHeader(request, JwtHelper.AUTHORIZATION_HEADER);

        if(StringUtils.hasText(accessToken)) {
            if(jwtHelper.validateTokenInternal(request, accessToken)) {
                Claims info = jwtHelper.getUserInfoFromClaims(accessToken);
                String userEmail = info.getSubject();

                if(refreshTokenService.isRefreshTokenPresent(userEmail)) {
                    setAuthentication(userEmail);
                } else{
                    request.setAttribute("exception", new CustomSecurityException(TokenErrorCode.INVALID_ACCESS_TOKEN));
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
