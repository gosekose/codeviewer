package codeview.main.test.presentation;

import codeview.main.test.application.TestCompileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class TestController {

    private final TestCompileService testCompileService;


    @PostMapping("/index")
    public String indexTest(@RequestBody String source) throws IOException {

        testCompileService.makeJavaSource(source);

        return null;
    }

}
