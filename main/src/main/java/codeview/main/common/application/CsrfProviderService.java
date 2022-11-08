package codeview.main.common.application;

import codeview.main.common.domain.Csrf;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class CsrfProviderService {


    public Csrf createCsrf(HttpServletRequest request) {

        CsrfToken token = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
        return Csrf.builder().token(token.getToken()).headerName(token.getHeaderName()).build();
    }

}
