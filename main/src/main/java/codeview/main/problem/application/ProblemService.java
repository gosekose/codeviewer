package codeview.main.problem.application;

import codeview.main.problem.domain.Problem;
import codeview.main.problem.infra.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

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


}
