package codeview.main.solve.application;

import codeview.main.solve.infra.repository.SolveQueryDslRepositoryImpl;
import codeview.main.solve.infra.repository.query.MemberSolveInfoCondition;
import codeview.main.solve.infra.repository.query.MemberSolveInfoDto;
import codeview.main.solve.infra.repository.query.SolvesOfProblemCondition;
import codeview.main.solve.infra.repository.query.SolvesOfProblemDto;
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

    @Cacheable(cacheNames = "myGroupMemberSolveInfoCrossJoin", key="#condition.groupId + #condition.memberId")
    public List<MemberSolveInfoDto> getMemberSolveInfoCrossJoin(MemberSolveInfoCondition condition) {
        return solveQueryDslRepository.searchMemberSolvesCrossJoin(condition);
    }

    @Cacheable(cacheNames = "myGroupMemberSolveInfo", key="#condition.groupId + #condition.problemId + #condition.memberId")
    public List<MemberSolveInfoDto> getMemberSolveInfo(MemberSolveInfoCondition condition) {
        return solveQueryDslRepository.searchMemberSolvesCrossJoin(condition);
    }

}
