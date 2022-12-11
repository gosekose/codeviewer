package codeview.main.businessservice.problemdescription.application;

import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problemdescription.domain.ProblemDescription;
import codeview.main.businessservice.problemdescription.infra.repository.ProblemDescriptionRepository;
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
public class ProblemDescriptionService {

    private final ProblemDescriptionRepository problemDescriptionRepository;

    @Transactional
    public Long save(ProblemDescription problemDescription) {
        ProblemDescription savedProblemDescription = problemDescriptionRepository.save(problemDescription);
        return savedProblemDescription.getId();
    }

    public ProblemDescription findById(Long id) {
        Optional<ProblemDescription> findProblemDescription = problemDescriptionRepository.findById(id);

        return findProblemDescription.orElseThrow(() -> {throw new IllegalArgumentException("존재 하지 않는 문제 설명입니다.");});
    }

//    @Cacheable(cacheNames = "problemDescription", key="#problem.id")
    public List<ProblemDescription> findAllByProblem(Problem problem) {
        return problemDescriptionRepository.findAllByProblem(problem);
    }

    public void deleteByProblemDescription(ProblemDescription problemDescription) {
        problemDescriptionRepository.delete(problemDescription);
    }

    public void flush() {
        problemDescriptionRepository.flush();
    }
}
