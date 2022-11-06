package codeview.main.token.infra.signature;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class RsaSecuritySigner extends SecuritySigner{

    @Override
    public String getJwtToken(UserDetails user, JWK jwk) throws JOSEException {

        log.info("***************************************************");
        log.info("jwt filter = {}", jwk);
        log.info("***************************************************");

        RSASSASigner jwsSigner =  new RSASSASigner(((RSAKey)jwk).toRSAPrivateKey());
        return super.getJwtTokenInternal(jwsSigner,user,jwk);
    }
}
