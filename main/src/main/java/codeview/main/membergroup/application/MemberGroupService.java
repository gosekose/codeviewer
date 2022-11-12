package codeview.main.membergroup.application;

import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.infra.repository.MemberGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberGroupService {

    private final MemberGroupRepository memberGroupRepository;

    public List<MemberGroup> findAllByMember(Member member) {
        List<MemberGroup> memberGroups = memberGroupRepository.findAllByMember(member);

        return memberGroups;
    }

    public MemberGroup findById(Long id) {
        Optional<MemberGroup> optionalMemberGroup = memberGroupRepository.findById(id);

        MemberGroup memberGroup = optionalMemberGroup.orElseThrow(
                () -> {
                    throw new IllegalArgumentException("일치하는 그룹이 없습니다.");
                }
        );

        return memberGroup;
    }

}
