package codeview.main.businessservice.solve.application;

import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.solve.domain.LastSolveStatus;
import codeview.main.businessservice.solve.domain.Solve;
import codeview.main.businessservice.solve.infra.repository.LastSolveStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class LastSolveStatusService {

    private final LastSolveStatusRepository lastSolveStatusRepository;

    @Transactional
    public Long save(Solve solve) {

        LastSolveStatus saveLastSolveStatus = lastSolveStatusRepository.save(
                LastSolveStatus.builder()
                        .member(solve.getMember())
                        .problem(solve.getProblem())
                        .solveStatus(solve.getSolveStatus())
                        .build()
        );

        return saveLastSolveStatus.getId();
    }

    public LastSolveStatus findByMemberAndProblem(Member member, Problem problem) {
        return lastSolveStatusRepository.findByMemberAndProblem(member, problem);
    }

}
