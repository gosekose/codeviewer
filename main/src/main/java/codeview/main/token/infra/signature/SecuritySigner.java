package codeview.main.token.infra.signature;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class SecuritySigner {

    protected String getJwtTokenInternal(JWSSigner jwsSigner, UserDetails user, JWK jwk) throws JOSEException {
        JWSHeader header = new JWSHeader.Builder((JWSAlgorithm) jwk.getAlgorithm()).keyID(jwk.getKeyID()).build();


        log.info("***************************************************");
        log.info("getJwtTokenInternal jwt filter = {}", jwk);
        log.info("***************************************************");

        List<String> authorities = user.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toList());
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject("user")
                .issuer("http://localhost:8080")
                .claim("username",user.getUsername())
                .claim("authority",authorities)
                .expirationTime(new Date(new Date().getTime() + 60 * 1000 * 60 * 5))
                .build();
        SignedJWT signedJWT = new SignedJWT(header,jwtClaimsSet);
        signedJWT.sign(jwsSigner);
        String jwtToken = signedJWT.serialize();

        return jwtToken;

    }

    public abstract String getJwtToken(UserDetails user, JWK jwk) throws JOSEException;


}
