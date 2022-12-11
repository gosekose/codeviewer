package codeview.main.businessservice.problem.application;

import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.infra.repository.ProblemRepository;
import codeview.main.businessservice.problem.presentation.dao.ProblemCreateDao;
import codeview.main.businessservice.problemdescription.application.ProblemDescriptionService;
import codeview.main.businessservice.problemdescription.application.ProblemIoExampleService;
import codeview.main.businessservice.problemdescription.domain.ProblemDescription;
import codeview.main.businessservice.problemdescription.domain.ProblemIoExample;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProblemEditService {

    private final ProblemService problemService;
    private final ProblemRepository problemRepository;
    private final ProblemIoExampleService problemIoExampleService;

    private final ProblemDescriptionService problemDescriptionService;

    @Transactional
    public Problem editProblem(Problem problem, ProblemCreateDao dao) {


        boolean problemFileChange = true;
        boolean ioZipFileChange = true;

        if (dao.getProblemFile() == null || dao.getProblemFile().isEmpty()) {
            problemFileChange = false;
        }

        if (dao.getIoZipFile() == null || dao.getIoZipFile().isEmpty()) {
            ioZipFileChange = false;
        }

        if (!problemFileChange && !ioZipFileChange) {

            problem.updateProblemNotFiles(dao);
            problemRepository.saveAndFlush(problem);

            return problem;
        }

        return null;
    }

    @Transactional
    public void editProblemIoExample(Problem problem, ProblemCreateDao dao) {
        List<ProblemIoExample> problemIoExamples = problemIoExampleService.findAllByProblem(problem);

        if (problemIoExamples.size() != dao.getInputs().size()) {

            if (problemIoExamples.size() > dao.getInputs().size()) {
                for (int i=0; i<problemIoExamples.size(); i++) {

                    if (i < dao.getInputs().size()) {
                        problemIoExamples.get(i).updateProblemIoExample(i+1, dao.getInputs().get(i), dao.getOutputs().get(i));
                    } else {
                        problemIoExampleService.deleteByProblemIoExample(problemIoExamples.get(i));
                    }
                }
            } else {
                for (int i=0; i<dao.getInputs().size(); i++) {

                    if (i < problemIoExamples.size()) {
                        problemIoExamples.get(i).updateProblemIoExample(i+1, dao.getInputs().get(i), dao.getOutputs().get(i));
                    } else {
                        problemIoExampleService.save(
                                ProblemIoExample.builder()
                                        .problem(problem)
                                        .inputSource(dao.getInputs().get(i))
                                        .outputSource(dao.getOutputs().get(i))
                                        .number(i+1)
                                        .build()
                        );
                    }
                }
            }
        } else {
            for (int i=0; i<problemIoExamples.size(); i++) {
                problemIoExamples.get(i).updateProblemIoExample(i+1, dao.getInputs().get(i), dao.getOutputs().get(i));
            }
        }
        problemIoExampleService.flush();
    }


    @Transactional
    public void editProblemDescription(Problem problem, ProblemCreateDao dao) {
        List<ProblemDescription> problemDescriptions = problemDescriptionService.findAllByProblem(problem);

        if (problemDescriptions.size() != dao.getDescriptions().size()) {

            if (problemDescriptions.size() > dao.getDescriptions().size()) {
                for (int i=0; i<problemDescriptions.size(); i++) {

                    if (i < dao.getDescriptions().size()) {
                        problemDescriptions.get(i).updateDescriptions(i+1, dao.getDescriptions().get(i));
                    } else {
                        problemDescriptionService.deleteByProblemDescription(problemDescriptions.get(i));
                    }
                }
            } else {
                for (int i=0; i<dao.getDescriptions().size(); i++) {

                    if (i < problemDescriptions.size()) {
                        problemDescriptions.get(i).updateDescriptions(i+1, dao.getDescriptions().get(i));
                    } else {
                        problemDescriptionService.save(
                                ProblemDescription.builder()
                                        .problem(problem)
                                        .number(i+1)
                                        .description(dao.getDescriptions().get(i))
                                        .build()
                        );
                    }
                }
            }
        } else {
            for (int i=0; i<problemDescriptions.size(); i++) {
                problemDescriptions.get(i).updateDescriptions(i+1, dao.getDescriptions().get(i));
            }
        }
        problemDescriptionService.flush();
    }

}
