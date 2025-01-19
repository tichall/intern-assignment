package sy.internproject.global.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sy.internproject.global.exception.CustomException;
import sy.internproject.global.exception.errorCode.TokenErrorCode;

import java.util.concurrent.TimeUnit;

import static sy.internproject.global.jwt.JwtHelper.BEARER_PREFIX;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {

    private static final String REFRESH_TOKEN_PREFIX = "refreshToken:";
    private static final long REFRESH_TOKEN_TTL = 7 * 24 * 60 * 60 * 1000L; // 만료 시간 7일

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveRefreshTokenInfo(String username, String refreshToken) {
        String parsedToken = refreshToken.substring(BEARER_PREFIX.length());
        String key = makeRefreshTokenKey(username);

        RefreshToken token = RefreshToken.of(
                parsedToken,
                REFRESH_TOKEN_TTL
        );

        redisTemplate.opsForValue()
                .set(key, token, REFRESH_TOKEN_TTL, TimeUnit.MILLISECONDS);
    }

    public boolean isRefreshTokenPresent(String username) {
        RefreshToken token = (RefreshToken) redisTemplate.opsForValue()
                .get(makeRefreshTokenKey(username));
        return token != null;
    }

    public void checkValidRefreshToken(String username, String refreshToken) {
        RefreshToken token = (RefreshToken) redisTemplate.opsForValue()
                .get(makeRefreshTokenKey(username));

        if (token == null) {
            throw new CustomException(TokenErrorCode.REFRESH_TOKEN_NOT_FOUND);
        } else {
            if (!refreshToken.equals(token.getTokenValue())) {
                throw new CustomException(TokenErrorCode.REFRESH_TOKEN_MISMATCH);
            }
        }
    }

    public void deleteRefreshTokenInfo(String username) {
        redisTemplate.delete(makeRefreshTokenKey(username));
    }

    private String makeRefreshTokenKey(String username) {
        return REFRESH_TOKEN_PREFIX + username;
    }
}
