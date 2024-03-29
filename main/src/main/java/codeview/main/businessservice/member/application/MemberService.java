package codeview.main.businessservice.member.application;

import codeview.main.auth.domain.users.social.ProviderUser;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.member.infra.MemberRepository;
import codeview.main.businessservice.member.infra.repository.MemberQueryDslRepositoryImpl;
import codeview.main.businessservice.member.infra.repository.query.GroupMemberInfo;
import codeview.main.businessservice.member.infra.repository.query.GroupMemberInfoCondition;
import codeview.main.businessservice.member.infra.repository.query.MemberCondition;
import codeview.main.businessservice.member.infra.repository.query.MemberProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryDslRepositoryImpl memberQueryDslRepository;


    @Cacheable(cacheNames = "member", key="#id")
    public Member find(Long id) {

        Optional<Member> findMember = memberRepository.findById(id);
        Member member = findMember.orElseThrow(() -> {throw new IllegalArgumentException("일치하는 회원이 없습니다.");});

        return member;
    }

    @Cacheable(cacheNames = "member", key="#email")
    public Member findByEmail(String email) {

        Member member = memberRepository.findByEmail(email);

        if(member == null) {
            throw new IllegalArgumentException("일치하는 회원이 없습니다.");
        }

        return member;
    }


    @Cacheable(cacheNames = "member", key="#registeredId")
    public Member findByRegisterId(String registeredId) {
        Optional<Member> findMember = memberRepository.findByRegisterId(registeredId);
        Member member = findMember.orElseThrow(
                () -> {
                    throw new IllegalArgumentException("일치하는 회원이 없습니다.");
                }
        );

        return member;
    }


    @Transactional
    public void register(String registrationId, ProviderUser providerUser) {

        Member member = Member.builder()
                .registrationId(registrationId)
                .registerId(providerUser.getId())
                .password(providerUser.getPassword())
                .authorities(String.valueOf(providerUser.getAuthorities().get(0)))
                .email(providerUser.getEmail())
                .picture(providerUser.getPicture())
                .build();

        memberRepository.save(member);
    }


    @Cacheable(value = "myMemberGroupInfo", key = "#condition.memberId")
    public GroupMemberInfo getGroupMemberInfo(GroupMemberInfoCondition condition) {
        return memberQueryDslRepository.searchMemberInfoUsingGroup(condition);
    }


    @Cacheable(value = "memberProfile", key = "#condition.memberId")
    public MemberProfileDto getMemberProfile(MemberCondition condition) {
        return memberQueryDslRepository.getMemberProfile(condition);
    }



//    public void update(Long id, MemberUpdateForm memberReq) {
//
//        Optional<Member> findMember = memberRepository.findById(id);
//        Member member = findMember.orElseThrow(() -> { throw new IllegalArgumentException("존재하지 않는 회원입니다."); });
//
//        Optional<School> findSchool = schoolRepository.findById(memberReq.getSchool().longValue());
//
//        if (findSchool == null) {
//            member.updateProfile(
//                    memberReq.getMemberName(),
//                    memberReq.getAge(),
//                    memberReq.getWork(),
//                    null,
//                    Address.builder()
//                            .zipCode(memberReq.getZipcode())
//                            .detail(memberReq.getDetails())
//                            .build());
//        } else {
//            member.updateProfile(
//                    memberReq.getMemberName(),
//                    memberReq.getAge(),
//                    memberReq.getWork(),
//                    findSchool.get(),
//                    Address.builder()
//                            .zipCode(memberReq.getZipcode())
//                            .detail(memberReq.getDetails())
//                            .build());
//        }
//
//    }
}
