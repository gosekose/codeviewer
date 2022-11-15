package codeview.main.problemdescription.application;

import codeview.main.problemdescription.domain.ProblemDescription;
import codeview.main.problemdescription.infra.repository.ProblemDescriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemDescriptionService {

    private final ProblemDescriptionRepository problemDescriptionRepository;

    public Long save(ProblemDescription problemDescription) {
        ProblemDescription savedProblemDescription = problemDescriptionRepository.save(problemDescription);
        return savedProblemDescription.getId();
    }

}
