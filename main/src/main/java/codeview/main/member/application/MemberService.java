package codeview.main.member.application;

import codeview.main.auth.domain.users.social.ProviderUser;
import codeview.main.member.domain.Member;
import codeview.main.member.infra.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member find(Long id) {

        Optional<Member> findMember = memberRepository.findById(id);
        Member member = findMember.orElseThrow(() -> {throw new IllegalArgumentException("일치하는 회원이 없습니다.");});

        return member;
    }

    public Member findByEmail(String email) {

        Member member = memberRepository.findByEmail(email);

        if(member == null) {
            throw new IllegalArgumentException("일치하는 회원이 없습니다.");
        }

        return member;
    }



    public Member findByRegisterId(String registeredId) {
        Optional<Member> findMember = memberRepository.findByRegisterId(registeredId);
        Member member = findMember.orElseThrow(
                () -> {
                    throw new IllegalArgumentException("일치하는 회원이 없습니다.");
                }
        );

        return member;
    }


    @Transactional(readOnly = false)
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

//    public void update(Long id, UpdateMemberRequest memberReq) {
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
