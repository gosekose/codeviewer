package codeview.main.businessservice.problem.application;

import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.domain.ProblemScore;
import codeview.main.businessservice.problem.infra.repository.ProblemScoreRepository;
import codeview.main.businessservice.problem.presentation.dao.ProblemCreateDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class ProblemScoreService {

    private final ProblemScoreRepository problemScoreRepository;

    @Transactional
    public void saveProblemScore(ProblemCreateDao problemCreateDao, Problem problem) {

        if (problemCreateDao == null || problemCreateDao.getScores() == null) {
            return ;
        }

        try{
            for(int i = 0; i< problemCreateDao.getScores().size(); i++) {
                problemScoreRepository.save(
                        ProblemScore.builder()
                                .problem(problem)
                                .number(i+1)
                                .score(problemCreateDao.getScores().get(i))
                                .build()
                );
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Transactional
    public Long save(ProblemScore problemScore) {
        ProblemScore savedProblemScore = problemScoreRepository.save(problemScore);
        return savedProblemScore.getId();
    }

    public List<ProblemScore> findProblemScoreByProblem(Problem problem) {
        return problemScoreRepository.findProblemScoreByProblem(problem);
    }

    public void flush() {
        problemScoreRepository.flush();
    }

    @Transactional
    public void deleteProblemScore(ProblemScore problemScore) {
        problemScoreRepository.delete(problemScore);
    }
}
