package sy.internproject.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sy.internproject.domain.dto.request.LoginRequestDto;
import sy.internproject.domain.dto.response.LoginResponseDto;
import sy.internproject.domain.entity.User;
import sy.internproject.domain.enums.UserStatus;
import sy.internproject.global.exception.errorCode.SecurityErrorCode;
import sy.internproject.global.jwt.JwtHelper;
import sy.internproject.global.jwt.RefreshTokenService;
import sy.internproject.global.security.UserDetailsImpl;
import sy.internproject.global.utils.ResponseUtil;

import java.io.IOException;

@Slf4j(topic = "인증 로그인 필터")
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtHelper jwtHelper;
    private final RefreshTokenService refreshTokenService;

    public LoginFilter(JwtHelper jwtHelper, RefreshTokenService refreshTokenService) {
        this.jwtHelper = jwtHelper;
        this.refreshTokenService = refreshTokenService;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword(), null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        User loginUser = userDetails.getUser();

        if(loginUser.getUserStatus() == UserStatus.ACTIVATE) {
            String username = loginUser.getUsername();
            String accessToken = jwtHelper.createAccessToken(username);
            String refreshToken = jwtHelper.createRefreshToken(username);

            refreshTokenService.saveRefreshTokenInfo(username, refreshToken);
            response.addHeader(JwtHelper.AUTHORIZATION_HEADER, accessToken);
            response.addHeader(JwtHelper.REFRESH_HEADER, refreshToken);

            LoginResponseDto responseDto = LoginResponseDto.of(accessToken);

            ResponseUtil.writeJsonResponse(response, HttpStatus.OK, responseDto);
        } else {
            ResponseUtil.writeJsonErrorResponse(response, SecurityErrorCode.WITHDRAWAL_USER);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.debug("로그인 실패 : {}", failed.getMessage());
        ResponseUtil.writeJsonErrorResponse(response, SecurityErrorCode.LOGIN_FAILED);
    }
}

