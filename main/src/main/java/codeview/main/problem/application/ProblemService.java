package codeview.main.problem.application;

import codeview.main.problem.domain.Problem;
import codeview.main.problem.infra.repository.ProblemQueryDslRepositoryImpl;
import codeview.main.problem.infra.repository.ProblemRepository;
import codeview.main.problem.infra.repository.query.ProblemListPageDto;
import codeview.main.problem.infra.repository.query.ProblemListSearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final ProblemQueryDslRepositoryImpl problemQueryDslRepository;

    @Transactional(readOnly = false)
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

    @Cacheable(cacheNames = "problemSearch", key = "#pageable.pageNumber")
    public Page<ProblemListPageDto> getSearchProblems(ProblemListSearchCondition condition, Pageable pageable) {
        return problemQueryDslRepository.searchPageComplex(condition, pageable);
    }

}
