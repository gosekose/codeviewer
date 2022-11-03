package resource.resource.infra.signiture;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.springframework.security.core.userdetails.UserDetails;

public class MackSecuritySigner extends SecuritySigner{

    @Override
    public String getJwtToken(UserDetails user, JWK jwk) throws JOSEException {

        MACSigner jwSinger = new MACSigner(((OctetSequenceKey) jwk).toOctetSequenceKey());
        return super.getJwtTokenInternal(jwSinger, user, jwk);
    }
}
