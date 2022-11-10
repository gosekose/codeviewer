package codeview.main.indextest.presentation;

import codeview.main.indextest.application.TestCompileService;
import codeview.main.indextest.presentation.dto.IndexTestDto;
import codeview.main.indextest.presentation.dto.IndexTestResultDto;
import codeview.main.indextest.presentation.dto.ResultIndexTestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestCompileService testCompileService;


    @CrossOrigin("http://localhost:5000/api/v1/test/index/compile")
    @PostMapping(value = "/api/v1/test/index")
    public ResponseEntity<String> indexTest(@RequestBody IndexTestDto indexTestDto,
                                    HttpSession httpSession) throws IOException {

        log.info(httpSession.getId());

        String message = testCompileService.makeJavaSource(httpSession, indexTestDto.getSource());

        return ResponseEntity.ok().body(message);
    }

    @PostMapping(value = "/api/v1/test/index/result")
    public ResultIndexTestDto indexResultTest(@RequestBody IndexTestResultDto indexTestResultDto) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(indexTestResultDto.getSourcePath()));

        String source = bufferedReader.readLine();

        return null;
    }
}
