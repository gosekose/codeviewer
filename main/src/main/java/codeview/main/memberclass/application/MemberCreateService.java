package codeview.main.memberclass.application;

import codeview.main.member.domain.Member;
import codeview.main.member.infra.MemberRepository;
import codeview.main.memberclass.application.dto.CreateClassesForm;
import codeview.main.memberclass.domain.MemberClasses;
import codeview.main.memberclass.infra.repository.MemberClassesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberCreateService {

    private final MemberClassesRepository memberClassesRepository;
    private final MemberRepository memberRepository;

    public MemberClasses createdClassesSave(Long id, CreateClassesForm form) {

        Optional<Member> findMember = memberRepository.findById(id);

        Member member = findMember.orElseThrow(
                () -> {
                    throw new IllegalArgumentException("일치하는 회원이 없습니다");
                });

        MemberClasses memberClasses = MemberClasses
                .builder()
                .className(form.getName())
                .maxMember(form.getMaxMember())
                .description(form.getDescription())
                .joinClosedTime(form.getJoinClosedTime())
                .skillTag(form.getSkillTag())
                .member(member)
                .password(form.getPassword())
                .memberClassesVisibility(form.getMemberClassesVisibility())
                .build();

        memberClassesRepository.save(memberClasses);

        return memberClasses;
    }

}
