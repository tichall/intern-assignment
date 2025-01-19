package sy.internproject.global.jwt;

import lombok.*;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class RefreshToken implements Serializable {

    private String tokenValue;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;

    public static RefreshToken of(String tokenValue, Long expiration) {
        return RefreshToken.builder()
                .tokenValue(tokenValue)
                .expiration(expiration)
                .build();
    }
}
