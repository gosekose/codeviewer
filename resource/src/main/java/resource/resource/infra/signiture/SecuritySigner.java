package resource.resource.infra.signiture;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SecuritySigner {

    protected String getJwtTokenInternal(MACSigner jwSinger, UserDetails user, JWK jwk) throws JOSEException {
        JWSHeader header = new JWSHeader.Builder((JWSAlgorithm) jwk.getAlgorithm()).keyID(jwk.getKeyID()).build();
        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject("user")
                .issuer("http://localhost:8081")
                .claim("username", user.getUsername())
                .claim("authoritiy", authorities)
                .expirationTime(new Date(new Date().getTime() + 60 * 1000 * 60 * 6))
                .build();

        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
        signedJWT.sign(jwSinger);

        return signedJWT.serialize();
    }

    public abstract String getJwtToken(UserDetails user, JWK jwk) throws JOSEException;
}
