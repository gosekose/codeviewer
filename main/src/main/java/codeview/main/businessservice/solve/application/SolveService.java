package codeview.main.businessservice.solve.application;

import codeview.main.businessservice.problem.domain.enumtype.ProblemLanguage;
import codeview.main.businessservice.solve.application.util.FileMaker;
import codeview.main.businessservice.solve.application.util.SolveJavaFileMaker;
import codeview.main.businessservice.solve.application.util.SolvePythonFileMaker;
import codeview.main.businessservice.solve.domain.LastSolveStatus;
import codeview.main.businessservice.solve.domain.Solve;
import codeview.main.businessservice.solve.infra.repository.SolveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class SolveService {

    private final SolveRepository solveRepository;
    private final LastSolveStatusService lastSolveStatusService;

    @Transactional
    public Long save(Solve solve) {
        Solve saveSolve = solveRepository.save(solve);

        LastSolveStatus lastSolveStatus = lastSolveStatusService.findByMemberAndProblem(solve.getMember(), solve.getProblem());

        if (lastSolveStatus == null) {
            lastSolveStatusService.save(saveSolve);
        }

        lastSolveStatus.updateSolveStatus(solve.getSolveStatus());

        return saveSolve.getId();
    }

    public FileMaker selectSolveFileMaker(ProblemLanguage problemLanguage) {
        FileMaker solveFileMaker;

        if (problemLanguage.equals(ProblemLanguage.python3)) {
            solveFileMaker = new SolvePythonFileMaker();
        } else if (problemLanguage.equals(ProblemLanguage.java11)){
            solveFileMaker = new SolveJavaFileMaker();
        } else {
            solveFileMaker = null;
        }

        log.info("solveFileMaker.class = {}", solveFileMaker.getClass());

        return solveFileMaker;

    }



}
