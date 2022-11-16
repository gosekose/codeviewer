package codeview.main.problemdescription.application;

import codeview.main.problem.domain.Problem;
import codeview.main.problemdescription.domain.ProblemIoExample;
import codeview.main.problemdescription.infra.repository.ProblemIoExampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProblemIoExampleService {

    private final ProblemIoExampleRepository problemIoExampleRepository;

    @Transactional(readOnly = false)
    public Long save(ProblemIoExample problemIoExample) {
        ProblemIoExample saveProblemIoExample = problemIoExampleRepository.save(problemIoExample);

        return saveProblemIoExample.getId();
    }

    public ProblemIoExample findById(Long id) {
        Optional<ProblemIoExample> findProblemIoExample = problemIoExampleRepository.findById(id);

        return findProblemIoExample.orElseThrow(
                () -> { throw new IllegalArgumentException("존재 하지 않는 입출력 예시 입니다."); });
    }

    public List<ProblemIoExample> findAllByProblem(Problem problem) {
        return problemIoExampleRepository.findAllByProblem(problem);
    }

}
