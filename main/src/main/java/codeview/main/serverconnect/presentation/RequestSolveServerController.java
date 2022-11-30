package codeview.main.serverconnect.presentation;

import codeview.main.serverconnect.presentation.dto.SolveRequestDto;
import codeview.main.serverconnect.presentation.dto.SolveResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/api/v1/server/solve")
public class RequestSolveServerController {


    @GetMapping
    public ResponseEntity<SolveResponseDto> getSolveResponse(SolveRequestDto solveRequestDto) throws JsonProcessingException {

        return null;
    }



}
