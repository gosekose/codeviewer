package codeview.main.businessservice.membership.application;

import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.member.infra.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class CommonDiscountPolicy implements DiscountPolicy {

    private final MemberRepository memberRepository;

    @Override
    public Member memberCheck(Long id) {

        Optional<Member> findMember = memberRepository.findById(id);

        return findMember.orElseThrow(() -> {throw new IllegalArgumentException("error");});
    }
}
