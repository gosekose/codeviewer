package codeview.main.board.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups/{groupId}/problems/board/problemList")
public class BoardRestController {

    @GetMapping("/{problemId}")
    public ResponseEntity<Integer> getProblemForBoardCheck(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("problemId") Integer problemId) {

        return new ResponseEntity<>(problemId, HttpStatus.OK);
    }
}
