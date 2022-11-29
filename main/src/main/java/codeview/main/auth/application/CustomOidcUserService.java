package codeview.main.auth.application;

import codeview.main.auth.application.certification.SelfCertification;
import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.auth.domain.users.social.ProviderUser;
import codeview.main.businessservice.member.application.MemberService;
import codeview.main.businessservice.member.infra.MemberRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends AbstractOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    public CustomOidcUserService(MemberService memberService, MemberRepository memberRepository, SelfCertification certification) {
        super(memberService, memberRepository, certification);
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        ClientRegistration clientRegistration = ClientRegistration
                                                .withClientRegistration(userRequest.getClientRegistration())
                                                .userNameAttributeName("sub")
                                                .build();
        OidcUserRequest oidcUserRequest =
                new OidcUserRequest(clientRegistration, userRequest.getAccessToken(),
                        userRequest.getIdToken(), userRequest.getAdditionalParameters());

        OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService = new OidcUserService();
        OidcUser oidcUser = oidcUserService.loadUser(oidcUserRequest);

        ProviderUser providerUser = super.providerUser(clientRegistration,oidcUser);

        selfCertificate(providerUser);

        super.register(providerUser, oidcUserRequest);

        return new PrincipalUser(providerUser);
    }
}
