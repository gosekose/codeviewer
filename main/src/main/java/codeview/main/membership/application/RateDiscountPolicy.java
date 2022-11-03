package codeview.main.membership.application;

import codeview.main.member.domain.Member;
import codeview.main.member.infra.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RateDiscountPolicy extends CommonDiscountPolicy{
    public RateDiscountPolicy(MemberRepository memberRepository) {
        super(memberRepository);
    }

    /**
     * rateDiscountPolicy  정책
     * 신규 유저의 경우 결제 금액 10% 할인
     * 신규 유저는 결제 생성이 기준 30일
     */


    @Override
    public int discountPayment(Long id) {

        Member member = memberCheck(id);

        if (member.getCreatedAt().getSecond() - LocalDateTime.now().getSecond() < 60 * 60 * 24 * 30) {

            if (member.getMembership().getPayment() > 0) {
                return (int) (member.getMembership().getPayment() / 10) * 100;
            }
        }

        return 0;
    }
}
