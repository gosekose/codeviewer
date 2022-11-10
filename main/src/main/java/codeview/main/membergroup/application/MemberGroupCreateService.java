package codeview.main.membergroup.application;

import codeview.main.member.domain.Member;
import codeview.main.member.infra.MemberRepository;
import codeview.main.membergroup.presentation.dto.CreateGroupForm;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.infra.repository.MemberGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberGroupCreateService {

    private final MemberGroupRepository memberGroupRepository;
    private final MemberRepository memberRepository;

    public MemberGroup createdGroupSave(Long id, CreateGroupForm form) {

        Optional<Member> findMember = memberRepository.findById(id);

        Member member = findMember.orElseThrow(
                () -> {
                    throw new IllegalArgumentException("일치하는 회원이 없습니다");
                });

        MemberGroup memberGroup = MemberGroup
                .builder()
                .name(form.getName())
                .maxMember(form.getMaxMember())
                .description(form.getDescription())
                .joinClosedTime(form.getJoinClosedTime())
                .skillTag(form.getSkillTag())
                .member(member)
                .password(form.getPassword())
                .memberGroupVisibility(form.getMemberGroupVisibility())
                .build();

        memberGroupRepository.save(memberGroup);

        return memberGroup;
    }

}
