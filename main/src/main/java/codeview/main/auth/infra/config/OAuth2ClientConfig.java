package codeview.main.auth.infra.config;

import codeview.main.auth.application.CustomOAuth2UserService;
import codeview.main.auth.application.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@EnableWebSecurity
@RequiredArgsConstructor
public class OAuth2ClientConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/static/**", "/static/js/**", "/static/images/**", "/static/css/**","/static/scss/**");
    }

    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> requests
                .antMatchers("/api/user")
                .access("hasAnyRole('SCOPE_profile','SCOPE_profile_image', 'SCOPE_email')")
                .antMatchers("/api/oidc")
                .access("hasRole('SCOPE_openid')")
                .antMatchers("/", "/resources/**")
                .permitAll()
                .anyRequest().authenticated());
        http.formLogin().loginPage("/login").loginProcessingUrl("/loginProc").defaultSuccessUrl("/").permitAll();
        http.oauth2Login(oauth2 -> oauth2.userInfoEndpoint(
                userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService)
                        .oidcUserService(customOidcUserService)));
        http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
        http.logout().logoutSuccessUrl("/");
        return http.build();
   }

}
