package codeview.main.membergroup.application;

import codeview.main.common.application.FolderMaker;
import codeview.main.member.domain.Member;
import codeview.main.member.infra.MemberRepository;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.infra.repository.MemberGroupRepository;
import codeview.main.membergroup.presentation.dto.CreateGroupForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberGroupCreateService {

    @Value("${file.dir}")
    private String fileDir;

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

        Long memberGroupId = memberGroup.getId();

        FolderMaker.folderMaker(fileDir, String.valueOf(memberGroupId));

        return memberGroup;
    }

}
