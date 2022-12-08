package codeview.main.businessservice.solve.application;

import codeview.main.businessservice.solve.domain.LastSolveStatus;
import codeview.main.businessservice.solve.domain.Solve;
import codeview.main.businessservice.solve.infra.repository.SolveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
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

}
