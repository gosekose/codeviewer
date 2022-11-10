package codeview.main.membergroup.application;

import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.infra.repository.MemberGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberGroupService {

    private final MemberGroupRepository memberGroupRepository;

    public List<MemberGroup> findAllByMember(Member member) {
        List<MemberGroup> memberGroups = memberGroupRepository.findAllByMember(member);

        return memberGroups;
    }

}
