package codeview.main.problemdescription.application;

import codeview.main.problemdescription.domain.ProblemIoExample;
import codeview.main.problemdescription.infra.repository.ProblemIoExampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemIoExampleService {

    private final ProblemIoExampleRepository problemIoExampleRepository;

    public Long save(ProblemIoExample problemIoExample) {
        ProblemIoExample saveProblemIoExample = problemIoExampleRepository.save(problemIoExample);

        return saveProblemIoExample.getId();
    }

}
