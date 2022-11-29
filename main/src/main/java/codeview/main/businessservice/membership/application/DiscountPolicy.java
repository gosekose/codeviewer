package codeview.main.businessservice.membership.application;

import codeview.main.businessservice.member.domain.Member;
import org.springframework.stereotype.Service;

@Service
public interface DiscountPolicy {

    public Member memberCheck(Long id);
    public int discountPayment(Long id);

}
