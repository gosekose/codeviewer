package codeview.main.auth.application;

import codeview.main.auth.application.certification.SelfCertification;
import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.auth.domain.users.social.ProviderUser;
import codeview.main.member.application.MemberService;
import codeview.main.member.infra.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomOAuth2UserService extends AbstractOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    public CustomOAuth2UserService(MemberService memberService, MemberRepository memberRepository, SelfCertification certification) {
        super(memberService, memberRepository, certification);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("customOauth2UserService");

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        ProviderUser providerUser = super.providerUser(clientRegistration, oAuth2User);

        selfCertificate(providerUser);

        super.register(providerUser, userRequest);

        return new PrincipalUser(providerUser);
    }
}
