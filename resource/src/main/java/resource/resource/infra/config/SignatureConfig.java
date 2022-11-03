package resource.resource.infra.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.gen.OctetKeyPairGenerator;
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import resource.resource.infra.signiture.MackSecuritySigner;

@Configuration
public class SignatureConfig {

    @Bean
    public MackSecuritySigner macSecuritySigner() {
        return new MackSecuritySigner();
    }

    @Bean
    public OctetSequenceKey octetSequenceKey() throws JOSEException {
        OctetSequenceKey octetSequenceKey = new OctetSequenceKeyGenerator(256)
                .keyID("macKey")
                .algorithm(JWSAlgorithm.HS256)
                .generate();

        return  octetSequenceKey;
    }

}
