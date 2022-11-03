package resource.resource.presentation.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member/token")
public class IndexController {

    @GetMapping
    public Authentication createToken(Authentication authentication) {
        return authentication;
    }

}
