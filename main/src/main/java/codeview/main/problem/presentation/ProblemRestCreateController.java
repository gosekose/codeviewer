package codeview.main.problem.presentation;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.membergroup.application.MemberGroupService;
import codeview.main.problem.application.ProblemService;
import codeview.main.problem.domain.UploadFile;
import codeview.main.problem.infra.config.PythonFileStore;
import codeview.main.problem.presentation.dto.ProblemCreateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/groups/admin")
@Slf4j
@RequiredArgsConstructor
public class ProblemRestCreateController {

    private final PythonFileStore pythonFileStore;
    private final MemberGroupService memberGroupService;
    private final ProblemService problemService;

    @PostMapping("/{groupId}/problems/new")
    public String postCreateProblem(
            @PathVariable("groupId") Integer groupId,
            @ModelAttribute ProblemCreateVO problemCreateVO,
            @AuthenticationPrincipal PrincipalUser principalUser,
            HttpServletRequest request) throws IOException {

        UploadFile uploadFile = pythonFileStore.storeFile(problemCreateVO.getInputFile(), String.valueOf(groupId));

        log.info("uploadFile.getUploadFileName = {}", uploadFile.getUploadFileName());
        log.info("uploadFile.getStoreFileName = {}", uploadFile.getStoreFileName());

        return "clear";
    }
}
