package codeview.main.token.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${jwt.keyId}")
    private String keyId;

    @Value("${jwt.tokenExpireTime}")
    private Long expireTime;

//    public String getUserEmailFromToken(String token) {
//        return getClaimFromToken(token, Claims::getId);
//    }
//
//    public <T> T getClaimsFormToken(String token, Function<Claims, T> claimsResolver)

}
