package codeview.main.serverconnect.presentation;

import codeview.main.serverconnect.application.service.HttpConnectionService;
import codeview.main.serverconnect.presentation.dto.SolveRequestDto;
import codeview.main.serverconnect.presentation.dto.SolveResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/api/v1/server/solve/test")
public class RequestSolveServerRestController {

    private final HttpConnectionService httpConnectionService;

    @GetMapping
    public ResponseEntity<SolveResponseDto> getSolveResponse(SolveRequestDto solveRequestDto) throws JsonProcessingException {

        SolveResponseDto solveResponseDto = httpConnectionService.requestSolveScore(solveRequestDto);

        log.info("solveId = {}", solveResponseDto.getSolveId());
        log.info("score = {}", solveResponseDto.getScore());
        log.info("testStatus = {}", solveResponseDto.getTestStatus());

        return new ResponseEntity<>(solveResponseDto, HttpStatus.OK);
    }



}
