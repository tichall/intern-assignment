package sy.internproject.global.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sy.internproject.global.exception.CustomSecurityException;
import sy.internproject.global.exception.errorCode.TokenErrorCode;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtProvider")
@Component
@RequiredArgsConstructor
public class JwtHelper {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "RefreshToken";
    public static final String BEARER_PREFIX = "Bearer ";

    // 액세스 토큰 만료시간 (30분)
    public static final long ACCESS_TOKEN_TIME = 30 * 60 * 1000L;

    // 리프레시 토큰 만료시간 (7일)
    public static final long REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000L;

    // JWT secret key
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] accessKeyBytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(accessKeyBytes);
    }

    public String createAccessToken(String username) {
        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String createRefreshToken(String username) {
        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String getJwtFromHeader(HttpServletRequest request, String header) {
        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    public boolean validateTokenInternal(HttpServletRequest request, String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            request.setAttribute("exception",
                    new CustomSecurityException(TokenErrorCode.EXPIRED_TOKEN));
        }
        return false;
    }

    public Claims getUserInfoFromClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String createTestToken(String subject, Date issuedAt, Date expiration) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issuedAt) // 1시간 전
                .setExpiration(expiration) // 30분 전
                .signWith(key, signatureAlgorithm)
                .compact();
    }
}
