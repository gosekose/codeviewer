package codeview.main.businessservice.problem.presentation.controller.admin;

import codeview.main.businessservice.membergroup.application.GroupService;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.problem.application.ProblemCreateService;
import codeview.main.businessservice.problem.application.ProblemService;
import codeview.main.businessservice.problem.domain.enumtype.ProblemDifficulty;
import codeview.main.businessservice.problem.domain.enumtype.ProblemLanguage;
import codeview.main.businessservice.problem.domain.enumtype.ProblemType;
import codeview.main.businessservice.problem.infra.repository.query.ProblemDetailPageCondition;
import codeview.main.businessservice.problem.infra.repository.query.ProblemDetailPageDto;
import codeview.main.businessservice.problem.presentation.utils.ProblemPage;
import codeview.main.common.application.CsrfProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/api/v1/groups/admin/{groupId}/problems")
@RequiredArgsConstructor
public class ProblemAdminUiController {

    private final ProblemPage problemPage;
    private final CsrfProviderService csrfProviderService;

    private final GroupService groupService;
    private final ProblemService problemService;
    private final ProblemCreateService problemCreateService;


    @ModelAttribute("problemDifficulties")
    public Map<String, ProblemDifficulty> regions() {
        Map<String, ProblemDifficulty> problemDifficultyMap = new LinkedHashMap<>();
        problemDifficultyMap.put("브론즈1", ProblemDifficulty.BRONZE1);
        problemDifficultyMap.put("브론즈2", ProblemDifficulty.BRONZE2);
        problemDifficultyMap.put("실버1", ProblemDifficulty.SILVER1);
        problemDifficultyMap.put("실버2", ProblemDifficulty.SILVER2);
        problemDifficultyMap.put("골드1", ProblemDifficulty.GOLD1);
        problemDifficultyMap.put("골드2", ProblemDifficulty.GOLD2);
        problemDifficultyMap.put("플래티넘1", ProblemDifficulty.PLATINUM1);
        problemDifficultyMap.put("플래티넘2", ProblemDifficulty.PLATINUM2);
        problemDifficultyMap.put("마스터", ProblemDifficulty.MASTER);
        return problemDifficultyMap;
    }

    @ModelAttribute("problemLanguages")
    public Map<String, ProblemLanguage> languages() {
        Map<String, ProblemLanguage> problemLanguageMap = new LinkedHashMap<>();
        problemLanguageMap.put("python3", ProblemLanguage.python3);
        problemLanguageMap.put("java11", ProblemLanguage.java11);
        return problemLanguageMap;
    }

    @ModelAttribute("problemTypes")
    public Map<String, ProblemType> problemType() {
        Map<String, ProblemType> problemTypeMap = new LinkedHashMap<>();
        problemTypeMap.put("과제형", ProblemType.HOMEWORKTYPE);
        problemTypeMap.put("시험형", ProblemType.TESTTYPE);
        return problemTypeMap;
    }

    @GetMapping("/new")
    public String getCreateProblem(
            @PathVariable("groupId") String groupId,
            HttpServletRequest request,
            Model model) {

        MemberGroup memberGroup = groupService.findById(Long.valueOf(groupId));

        model.addAttribute("_csrf", csrfProviderService.createCsrf(request));
        model.addAttribute("groupId", groupId);

        problemCreateService.deleteNotFolderPath(memberGroup);

        return "problems/admins/create-my-problem";
    }

    @GetMapping("/{problemId}")
    public String getProblemIdAdminPage(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("problemId") Integer problemId,
            Model model) throws MalformedURLException {

        problemPage.getProblemPage(model, groupId, problemId);

        log.info("problemAdmin groupId = {}, problemId = {}", groupId, problemId);

        return "problems/admins/admin-my-problem";
    }

    @GetMapping("/{problemId}/edit")
    public String getProblemIdEditPage(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("problemId") Integer problemId,
            Model model) throws MalformedURLException {

        problemPage.getProblemPage(model, groupId, problemId);

        log.info("problemAdmin groupId = {}, problemId = {}", groupId, problemId);

        return "problems/admins/edit-my-problem";
    }



    @GetMapping
    public String getProblemsAdminPage(@PathVariable("groupId") Integer groupId,
                                       Model model,
                                       ProblemDetailPageCondition condition,
                                       Pageable pageable) {

        condition.setMemberGroup(groupService.findById(Long.valueOf(groupId)));
        Page<ProblemDetailPageDto> searchProblems = problemService.getDetailProblems(condition, pageable);
        model.addAttribute("groupId", groupId);
        model.addAttribute("problems", searchProblems);

        return "problems/admins/my-problem-list";
    }

}
