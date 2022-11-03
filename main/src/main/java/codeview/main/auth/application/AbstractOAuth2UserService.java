package codeview.main.auth.application;


import codeview.main.auth.application.certification.SelfCertification;
import codeview.main.auth.domain.users.social.*;
import codeview.main.auth.infra.common.util.OAuth2Utils;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.member.infra.MemberRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Getter
public abstract class AbstractOAuth2UserService {

    private final MemberService memberService;

    private final MemberRepository memberRepository;

    private final SelfCertification certification;

    @Autowired
    public AbstractOAuth2UserService(MemberService memberService, MemberRepository memberRepository, SelfCertification certification) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.certification = certification;
    }

    public void selfCertificate(ProviderUser providerUser){
        log.info("providerUser = {}", providerUser.getAttributes());
        certification.checkCertification(providerUser);
    }
    public void register(ProviderUser providerUser, OAuth2UserRequest userRequest){

        Member member = memberRepository.findByEmail(providerUser.getEmail());

        if(member == null){
            ClientRegistration clientRegistration = userRequest.getClientRegistration();
            memberService.register(clientRegistration.getRegistrationId(),providerUser);
        }else{
            System.out.println("userRequest = " + userRequest);
        }
    }

    public ProviderUser providerUser(ClientRegistration clientRegistration, OAuth2User oAuth2User) {

        String registrationId = clientRegistration.getRegistrationId();

        log.info("registrationId = {}", registrationId);

        switch (registrationId) {

            case "google":
                return new GoogleUser(OAuth2Utils.getMainAttributes(oAuth2User), oAuth2User, clientRegistration);

            case "naver":
                log.info("provider name = Naver ");
                log.info("OAuth2Utils.getSubAttributes(oAuth2User, response) = {}", OAuth2Utils.getSubAttributes(oAuth2User, "response"));
                return new NaverUser(OAuth2Utils.getSubAttributes(oAuth2User, "response"), oAuth2User, clientRegistration);

            case "kakao":
                if (oAuth2User instanceof OidcUser) {
                    return new KakaoOidcUser(OAuth2Utils.getMainAttributes(oAuth2User), oAuth2User, clientRegistration);

                } else {
                    return new KakaoUser(OAuth2Utils.getOtherAttributes(oAuth2User, "kakao_account", "profile"), oAuth2User, clientRegistration);
                }

            default:
                return null;
        }
    }
}
