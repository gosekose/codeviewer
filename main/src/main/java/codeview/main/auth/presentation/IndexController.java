package codeview.main.auth.presentation;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.auth.infra.common.util.OAuth2Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;

@Slf4j
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, Authentication authentication, @AuthenticationPrincipal PrincipalUser principalUser) {

        String view = "index";

        if (authentication != null) {

            String userName;
            if (authentication instanceof OAuth2AuthenticationToken) {
                userName = OAuth2Utils.oAuth2UserName((OAuth2AuthenticationToken) authentication, principalUser);
            } else {
                userName = principalUser.getProviderUser().getUsername();
            }

            model.addAttribute("user", userName);
            model.addAttribute("provider", principalUser.getProviderUser().getProvider().toUpperCase());

            if(!principalUser.getProviderUser().isCertificated()) view = "selfcert";
        }
        return view;
    }
}