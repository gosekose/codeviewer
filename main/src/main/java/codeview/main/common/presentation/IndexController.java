package codeview.main.common.presentation;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.auth.infra.common.util.OAuth2Utils;
import codeview.main.businessservice.member.application.MemberService;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.common.application.CsrfProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final CsrfProviderService csrfProviderService;
    private final MemberService memberService;

    @CrossOrigin("http://localhost:5000/api/v1/test/index/compile")
    @GetMapping("/")
    public String index(Model model, Authentication authentication,
                        HttpServletRequest request,
                        @AuthenticationPrincipal PrincipalUser principalUser) {

        if (authentication != null) {

            String userName;
            if (authentication instanceof OAuth2AuthenticationToken) {
                userName = OAuth2Utils.oAuth2UserName((OAuth2AuthenticationToken) authentication, principalUser);
            } else {
                userName = principalUser.getProviderUser().getUsername();
            }

            Member member = memberService.findByRegisterId(principalUser.getProviderUser().getId());

            model.addAttribute("user", userName);
//            model.addAttribute("provider", principalUser.getProviderUser().getProvider().toUpperCase());
        }

        model.addAttribute("_csrf", csrfProviderService.createCsrf(request));
//        model.addAttribute("indexTestForm", new IndexTestForm());

        return "index";
    }

}