package codeview.main.businessservice.problem.application;

import codeview.main.businessservice.membergroup.application.GroupService;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.domain.embedded.ProblemInputIoFile;
import codeview.main.businessservice.problem.infra.repository.ProblemQueryDslRepositoryImpl;
import codeview.main.businessservice.problem.infra.repository.ProblemRepository;
import codeview.main.businessservice.problem.infra.repository.query.*;
import codeview.main.common.application.FolderRemover;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProblemService {

    private final GroupService groupService;
    private final ProblemRepository problemRepository;
    private final ProblemQueryDslRepositoryImpl problemQueryDslRepository;
    private final FolderRemover folderRemover;

    @Transactional
    public Long save(Problem problem) {
        Problem savedProblem = problemRepository.save(problem);
        return savedProblem.getId();
    }

    public Problem findById(Long id) {
        Optional<Problem> findProblem = problemRepository.findById(id);

        return findProblem.orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재 하지 않는 문제입니다.");
                }
        );

    }

    @Cacheable(cacheNames = "problemForBoard", key = "#condition.memberGroupId + #pageable.pageNumber")
    public Page<ProblemSearchForBoardDto> getProblemForBoard(ProblemSearchPageCondition condition, Pageable pageable) {
        return problemQueryDslRepository.searchProblemForBoard(condition, pageable);
    }

    @Cacheable(cacheNames = "problemSearch",
            key = "#condition.memberGroup + #condition.creator + #condition.myMember + #condition.name + pageable.pageNumber")
    public Page<ProblemDetailPageDto> getDetailProblems(ProblemDetailPageCondition condition, Pageable pageable) {
        return problemQueryDslRepository.searchDetailPageComplex(condition, pageable);
    }

    public Page<ProblemSearchPageDto> getProblemSearchPage(ProblemSearchPageCondition condition, Pageable pageable) {
        return problemQueryDslRepository.searchProblemPageComplex(condition, pageable);
    }

    public List<ProblemInputIoFile> findByMemberGroupForInputFolderPath(MemberGroup memberGroup) {
        return problemRepository.findByMemberGroupForInputFolderPath(memberGroup);
    }

}
