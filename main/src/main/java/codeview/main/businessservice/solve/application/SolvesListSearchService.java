package codeview.main.businessservice.solve.application;

import codeview.main.businessservice.solve.infra.repository.SolveQueryDslRepositoryImpl;
import codeview.main.businessservice.solve.infra.repository.query.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SolvesListSearchService {

    private final SolveQueryDslRepositoryImpl solveQueryDslRepository;

    @Cacheable(cacheNames = "memberAllSolveSearch", key="#condition.problemId")
    public Page<SolvesOfProblemDto> getSolvesOfProblemPages(SolvesOfProblemCondition condition, Pageable pageable) {
        return solveQueryDslRepository.searchPageComplex(condition, pageable);
    }

    @Cacheable(cacheNames = "memberSolveSearch", key="#condition.problemId + #condition.memberId")
    public List<SolvesOfProblemDto> getSolvesOfProblem(SolvesOfProblemCondition condition, Pageable pageable) {
        return solveQueryDslRepository.searchSolvesOfProblemDto(condition);
    }

    @Cacheable(cacheNames = "myGroupMemberSolveNoProblemId", key="#condition.groupId + #condition.memberId")
    public List<MemberSolveInfoDto> getMemberSolveInfoNoProblemId(MemberSolveInfoCondition condition) {
        return solveQueryDslRepository.searchMemberSolvesNoProblemId(condition);
    }

    @Cacheable(cacheNames = "myGroupMemberSolveInfo", key="#condition.groupId + #condition.problemId + #condition.memberId")
    public List<MemberSolveInfoDto> getMemberSolveInfo(MemberSolveInfoCondition condition) {
        return solveQueryDslRepository.searchMemberSolvesNoProblemId(condition);
    }

    @Cacheable(cacheNames = "mySolveChart", key = "#condition.problemId + #condition.memberId")
    public List<SolvesOfProblemChartMyScoreDto> getSolvesOfProblemChartMyScoreDto(MemberSolveInfoCondition condition) {
        return solveQueryDslRepository.searchMemberSolvesMyChartDto(condition);
    }


    @Cacheable(cacheNames = "otherSolveChart", key = "#condition.problemId + #condition.solveCount")
    public List<SolvesOfProblemChartOtherScoreDto> getSolvesOfProblemChartOtherScoreDto(MemberSolveInfoCondition condition) {
        return solveQueryDslRepository.searchMemberSolvesOtherChartDto(condition);
    }

}
