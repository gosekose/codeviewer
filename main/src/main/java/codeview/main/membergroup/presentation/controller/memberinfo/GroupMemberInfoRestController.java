package codeview.main.membergroup.presentation.controller.memberinfo;

import codeview.main.member.application.MemberService;
import codeview.main.membergroup.presentation.dao.SolveChartInfo;
import codeview.main.solve.application.SolvesListSearchService;
import codeview.main.solve.infra.repository.query.MemberSolveInfoCondition;
import codeview.main.solve.infra.repository.query.SolvesOfProblemChartMyScoreDto;
import codeview.main.solve.infra.repository.query.SolvesOfProblemChartOtherScoreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/groups/admin/{groupId}/members/{memberId}/{problemId}")
@RequiredArgsConstructor
public class GroupMemberInfoRestController {

    private final MemberService memberService;
    private final SolvesListSearchService solvesListSearchService;

    @GetMapping("/chart")
    public ResponseEntity<SolveChartInfo> getMemberInfo(@PathVariable("groupId") Integer groupId,
                                        @PathVariable("memberId") Integer memberId,
                                        @PathVariable("problemId") Integer problemId) {

        MemberSolveInfoCondition condition = new MemberSolveInfoCondition();
        condition.updateMemberProblem(memberId, problemId);

        List<SolvesOfProblemChartMyScoreDto> myChart = solvesListSearchService.getSolvesOfProblemChartMyScoreDto(condition);

        condition.updateSolveCount(myChart.size());
        List<SolvesOfProblemChartOtherScoreDto> otherChart = solvesListSearchService.getSolvesOfProblemChartOtherScoreDto(condition);

        SolveChartInfo solveChartInfo = SolveChartInfo
                .builder()
                .myChart(myChart)
                .otherChart(otherChart)
                .build();

        return new ResponseEntity(solveChartInfo, HttpStatus.OK);
    }


}
