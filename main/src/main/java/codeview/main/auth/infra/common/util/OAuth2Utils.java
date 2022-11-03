package codeview.main.auth.infra.common.util;

import codeview.main.auth.domain.Attributes;
import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.auth.infra.common.enums.OAuth2Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Slf4j
public class OAuth2Utils {

    public static Attributes getMainAttributes(OAuth2User oAuth2User) {

        return Attributes.builder()
                .mainAttributes(oAuth2User.getAttributes())
                .build();
    }

    public static Attributes getSubAttributes(OAuth2User oAuth2User, String mainAttributesKey) {

        Map<String, Object> subAttributes = (Map<String, Object>) oAuth2User.getAttributes().get(mainAttributesKey);

        log.info("oauth2User = {}", oAuth2User.getAttributes());
        log.info("mainAttributesKey = {}", mainAttributesKey);
        log.info("subAttributes = {}", subAttributes);

        return Attributes
                .builder()
                .subAttributes(subAttributes)
                .build();
    }

    public static Attributes getOtherAttributes(OAuth2User oAuth2User, String mainAttributesKey, String subAttributesKey) {

        Map<String, Object> subAttributes = (Map<String, Object>) oAuth2User.getAttributes().get(mainAttributesKey);
        Map<String, Object> otherAttributes = (Map<String, Object>) subAttributes.get(subAttributesKey);

        return Attributes.builder()
                .subAttributes(subAttributes)
                .otherAttributes(otherAttributes)
                .build();
    }

    public static String oAuth2UserName(OAuth2AuthenticationToken authentication, PrincipalUser principalUser) {

        String userName;
        String registrationId = authentication.getAuthorizedClientRegistrationId();
        OAuth2User oAuth2User = principalUser.getProviderUser().getOAuth2User();

        Attributes attributes = OAuth2Utils.getMainAttributes(oAuth2User);
        userName = (String) attributes.getMainAttributes().get("name");

        // Naver
        if (registrationId.equals(OAuth2Config.SocialType.NAVER.getSocialName())) {
            attributes = OAuth2Utils.getSubAttributes(oAuth2User, "response");
            userName = (String) attributes.getSubAttributes().get("name");
            log.info("attributes = {}", attributes);
            log.info("username = {}", userName);


        // Kakao
        } else if (registrationId.equals(OAuth2Config.SocialType.KAKAO.getSocialName())) {

            // OpenID Connect
            if (oAuth2User instanceof OidcUser) {
                attributes = OAuth2Utils.getMainAttributes(oAuth2User);
                userName = (String) attributes.getMainAttributes().get("nickname");

            } else {
                attributes = OAuth2Utils.getOtherAttributes(principalUser, "kakao_account", "profile");
                userName = (String) attributes.getOtherAttributes().get("nickname");
            }
        }
        return userName;
    }
}
