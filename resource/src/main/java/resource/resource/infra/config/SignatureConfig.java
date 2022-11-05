package resource.resource.infra.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import resource.resource.infra.signiture.MackSecuritySigner;
import resource.resource.infra.signiture.RsaSecuritySigner;

import java.security.interfaces.RSAPublicKey;

@Configuration
public class SignatureConfig {

    @Value("${jwt.keyId}")
    String keyId;

    @Bean
    public MackSecuritySigner macSecuritySigner() {
        return new MackSecuritySigner();
    }

    @Bean
    public OctetSequenceKey octetSequenceKey() throws JOSEException {

        String name;
        OctetSequenceKey octetSequenceKey = new OctetSequenceKeyGenerator(256)
                .keyID(keyId)
                .algorithm(JWSAlgorithm.HS256)
                .generate();

        return  octetSequenceKey;
    }

    @Bean
    public RsaSecuritySigner rsaSecuritySigner() {
        return new RsaSecuritySigner();
    }

    @Bean
    public RSAKey rsaKey() throws JOSEException {
        RSAKey rsaKey = new RSAKeyGenerator(2048)
                .keyID(keyId)
                .algorithm(JWSAlgorithm.RS512)
                .generate();

        return rsaKey;
    }

}
