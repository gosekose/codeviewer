package resource.resource.infra.signiture;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.JWK;
import org.springframework.security.core.userdetails.UserDetails;

public class RsaSecuritySigner extends SecuritySigner {
    @Override
    protected String getJwtTokenInternal(MACSigner jwSinger, UserDetails user, JWK jwk) throws JOSEException {
        return super.getJwtTokenInternal(jwSinger, user, jwk);
    }

    @Override
    public String getJwtToken(UserDetails user, JWK jwk) {
        return null;
    }
}
