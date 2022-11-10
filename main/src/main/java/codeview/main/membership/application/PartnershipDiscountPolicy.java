package codeview.main.membership.application;

import codeview.main.member.domain.Member;
import codeview.main.member.infra.MemberRepository;
import codeview.main.school.domain.School;
import codeview.main.school.infra.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartnershipDiscountPolicy extends CommonDiscountPolicy {

    private final SchoolRepository schoolRepository;

    public PartnershipDiscountPolicy(MemberRepository memberRepository, SchoolRepository schoolRepository) {
        super(memberRepository);
        this.schoolRepository = schoolRepository;
    }

    @Override
    public int discountPayment(Long id) {

        Member member = memberCheck(id);

        if (member.getSchool() == null) {
            return 0;
        } else {
            School school = schoolCheck(member);

            if (school.getSchoolMembership() != null) {
                return member.getMembership().getPayment();
            }
        }

        return 0;

    }

    private School schoolCheck(Member member) {
        Optional<School> findSchool = schoolRepository.findById(member.getSchool().getId());
        School school = findSchool.orElseThrow(() -> {
            throw new IllegalArgumentException("일치하는 학교가 없습니다.");
        });
        return school;
    }
}
