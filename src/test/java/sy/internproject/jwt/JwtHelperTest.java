package sy.internproject.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import sy.internproject.global.jwt.JwtHelper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Base64;
import java.util.Date;

class JwtHelperTest {

    @InjectMocks
    private static JwtHelper jwtHelper;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jwtHelper, "secretKey", Base64.getEncoder().encodeToString("testSecretKey123412341234123412341234".getBytes()));
        jwtHelper.init();
    }

    @Test
    void createAccessToken_ShouldReturnValidToken() {
        String token = jwtHelper.createAccessToken("testUsername");
        assertTrue(token.startsWith(JwtHelper.BEARER_PREFIX));
        assertTrue(jwtHelper.validateTokenInternal(request, token.substring(JwtHelper.BEARER_PREFIX.length())));
    }

    @Test
    void createRefreshToken_ShouldReturnValidToken() {
        String token = jwtHelper.createAccessToken("testUsername");
        assertTrue(token.startsWith(JwtHelper.BEARER_PREFIX));
        assertTrue(jwtHelper.validateTokenInternal(request, token.substring(JwtHelper.BEARER_PREFIX.length())));
    }

    @Test
    void getJwtFromHeader_ShouldReturnTokenValue() {
        String tokenValue = "validTokenValue";
        String validToken = JwtHelper.BEARER_PREFIX + tokenValue;
        given(request.getHeader(JwtHelper.AUTHORIZATION_HEADER)).willReturn(validToken);

        String result = jwtHelper.getJwtFromHeader(request, JwtHelper.AUTHORIZATION_HEADER);
        assertEquals(tokenValue, result);
    }

    @Test
    void getJwtFromHeader_ShouldReturnNull() {
        String invalidTokenValue = "invalidTokenValue";
        given(request.getHeader(JwtHelper.AUTHORIZATION_HEADER)).willReturn(invalidTokenValue);

        String result = jwtHelper.getJwtFromHeader(request, JwtHelper.AUTHORIZATION_HEADER);
        assertNull(result);
    }

    @Test
    void validateTokenInternal_ShouldReturnTrue() {
        String accessToken = jwtHelper.createAccessToken("testUser");
        boolean result = jwtHelper.validateTokenInternal(request, accessToken.substring(JwtHelper.BEARER_PREFIX.length()));
        assertTrue(result);
    }

    @Test
    void validateTokenInternal_ShouldReturnFalse() {
        String expiredToken = createExpiredToken();
        boolean result = jwtHelper.validateTokenInternal(request, expiredToken);
        assertFalse(result);
    }

    private String createExpiredToken() {
        long currentTime = System.currentTimeMillis();
        Date issuedAt = new Date(currentTime - 1000 * 60 * 60); // 1시간 전
        Date expiration = new Date(currentTime - 1000 * 60 * 30); // 30분 전

        return jwtHelper.createTestToken("testUser", issuedAt, expiration);
    }
}