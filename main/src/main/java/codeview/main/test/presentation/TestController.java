package codeview.main.test.presentation;

import codeview.main.test.application.TestCompileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class TestController {

    private final TestCompileService testCompileService;

    @PostMapping("/index")
    public String indexTest(@RequestBody String source,
                            HttpServletRequest request,
                            HttpSession httpSession) throws IOException {

        log.info(httpSession.getId());

        testCompileService.makeJavaSource(httpSession, source);

        return "redirect:/";
    }

}
