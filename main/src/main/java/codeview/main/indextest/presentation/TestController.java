package codeview.main.indextest.presentation;

import codeview.main.indextest.application.TestCompileService;
import codeview.main.indextest.application.dto.IndexTestDto;
import codeview.main.indextest.application.dto.IndexTestResultDto;
import codeview.main.indextest.application.dto.ResultIndexTestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestCompileService testCompileService;

    @PostMapping(value = "/api/v1/test/index")
    public ResultIndexTestDto indexTest(@RequestBody IndexTestDto indexTestDto,
                                        HttpSession httpSession) throws IOException {

        log.info(httpSession.getId());

        String checkStatus = testCompileService.makeJavaSource(httpSession, indexTestDto.getSource());

        return new ResultIndexTestDto(checkStatus);
    }

    @PostMapping(value = "/api/v1/test/index/result")
    public ResultIndexTestDto indexResultTest(@RequestBody IndexTestResultDto indexTestResultDto) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(indexTestResultDto.getSourcePath()));

        String source = bufferedReader.readLine();

        return null;
    }
}
