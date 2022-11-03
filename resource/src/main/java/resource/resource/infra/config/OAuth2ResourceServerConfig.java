package resource.resource.infra.config;

import com.nimbusds.jose.jwk.OctetSequenceKey;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import resource.resource.infra.filter.authentication.JwtAuthenticationFilter;
import resource.resource.infra.filter.authorization.JwtAuthorizationMacFilter;
import resource.resource.infra.signiture.MackSecuritySigner;

import javax.servlet.Filter;

//@Configuration(proxyBeanMethods = false
@Configuration
@RequiredArgsConstructor
public class OAuth2ResourceServerConfig {

    private final OAuth2ResourceServerProperties resourceServerProperties;
    private final MackSecuritySigner macSecuritySigner;
    private final OctetSequenceKey octetSequenceKey;

    @Bean
    public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests((requests) -> requests
                .antMatchers("/")
                .permitAll()
                .anyRequest().authenticated());
//        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        http.userDetailsService(userDetailsService());
        http.addFilterBefore(jwtAuthenticationFilter(macSecuritySigner, octetSequenceKey), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthorizationMacFilter(octetSequenceKey), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public Filter jwtAuthorizationMacFilter(OctetSequenceKey octetSequenceKey) {
        return new JwtAuthorizationMacFilter(octetSequenceKey);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(MackSecuritySigner macSecuritySigner, OctetSequenceKey octetSequenceKey) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(macSecuritySigner, octetSequenceKey);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager(null));
        return jwtAuthenticationFilter;
    }

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return JwtDecoders.fromIssuerLocation(resourceServerProperties.getJwt().getIssuerUri());
//    }
//

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user = User.withUsername("user").password("1234").authorities("ROLE_USER").build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
