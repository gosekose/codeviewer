package codeview.main.membergroup.application;

import codeview.main.common.application.FolderMaker;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.member.infra.MemberRepository;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.infra.repository.membergroup.MemberGroupRepository;
import codeview.main.membergroup.presentation.dto.CreateGroupForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {

    @Value("${file.dir}")
    private String fileDir;

    private final MemberGroupRepository memberGroupRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public List<MemberGroup> findAllByMember(Member member) {
        List<MemberGroup> memberGroups = memberGroupRepository.findAllByMember(member);

        return memberGroups;
    }

    @Cacheable(cacheNames = "memberGroup", key="#id")
    public MemberGroup findById(Long id) {
        Optional<MemberGroup> optionalMemberGroup = memberGroupRepository.findById(id);

        MemberGroup memberGroup = optionalMemberGroup.orElseThrow(
                () -> {
                    throw new IllegalArgumentException("일치하는 그룹이 없습니다.");
                }
        );

        return memberGroup;
    }

    @Transactional
    public MemberGroup createdGroupSave(Long id, CreateGroupForm form) {

        Member member = memberService.find(id);

        log.info("groupForm auto join = {}", form.getGroupAutoJoin());

        MemberGroup memberGroup = MemberGroup
                .builder()
                .name(form.getName())
                .maxMember(form.getMaxMember())
                .description(form.getDescription())
                .joinClosedTime(form.getJoinClosedTime())
                .skillTag(form.getSkillTag())
                .creator(member)
                .password(form.getPassword())
                .groupAutoJoin(form.getGroupAutoJoin())
                .memberGroupVisibility(form.getMemberGroupVisibility())
                .build();

        memberGroupRepository.save(memberGroup);

        Long memberGroupId = memberGroup.getId();

        FolderMaker.folderMaker(fileDir, String.valueOf(memberGroupId));

        return memberGroup;
    }


}
