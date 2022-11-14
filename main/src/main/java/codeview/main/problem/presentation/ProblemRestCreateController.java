package codeview.main.problem.presentation;

import codeview.main.common.infra.WebJsonConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/problem")
@Slf4j
@RequiredArgsConstructor
public class ProblemRestCreateController {

    private final WebJsonConfig webJsonConfig;

    @PostMapping("/new")
    public String postCreateProblem(@RequestBody Map<String, Object> map) throws JsonProcessingException {

        log.info("map = {}", map);

        Object name = map.get("name");
        log.info(name.toString());

        return "clear";
    }
}
