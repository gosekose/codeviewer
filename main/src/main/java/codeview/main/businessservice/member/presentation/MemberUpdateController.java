package codeview.main.businessservice.member.presentation;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.businessservice.member.application.MemberService;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.member.infra.repository.query.MemberCondition;
import codeview.main.businessservice.member.infra.repository.query.MemberProfileDto;
import codeview.main.businessservice.member.presentation.dto.MemberUpdateForm;
import codeview.main.businessservice.school.application.SchoolService;
import codeview.main.businessservice.school.infra.dao.SchoolSearchCondition;
import codeview.main.businessservice.school.infra.repository.SchoolQueryDslRepositoryImpl;
import codeview.main.businessservice.school.presentation.dto.AllSchoolDto;
import codeview.main.businessservice.school.presentation.dto.AllSchoolResult;
import codeview.main.businessservice.school.presentation.dto.SchoolDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberUpdateController {

    private final MemberService memberService;
    private final SchoolService schoolService;
    private final SchoolQueryDslRepositoryImpl schoolQueryDslRepository;

    @GetMapping("/id/update-info/schools")
    public AllSchoolResult allSchoolResult() {

        List<AllSchoolDto> allSchools = schoolService.findAll().stream().map(s -> new AllSchoolDto(s.getSchoolName(), s.getAddress()))
                .collect(Collectors.toList());

        return new AllSchoolResult<>(allSchools.size(), allSchools);
    }

    @GetMapping("/id/update-info/specific-schools")
    public Page<SchoolDto> specificSchoolResult(SchoolSearchCondition schoolSearchCondition, Pageable pageable) {

        return schoolQueryDslRepository.searchPageComplex(schoolSearchCondition, pageable);
    }

//    @PostMapping("/{id}/profile")
//    public MemberResponseDto updateMember(@PathVariable Long id,
//                                          @RequestBody MemberUpdateForm updateMemberRequest) {
//
//        memberService.update(id, updateMemberRequest);
//        Member member = memberService.find(id);
//
//        return new MemberResponseDto(member.getId(), member);
//    }


    @GetMapping("/profile")
    public String getUpdateMember(
            Model model,
            MemberCondition condition,
            @AuthenticationPrincipal PrincipalUser principalUser) {

        Member member = memberService.findByRegisterId(principalUser.getProviderUser().getId());

        condition.updateMember(member.getId());

        MemberProfileDto memberProfileDto = memberService.getMemberProfile(condition);

        log.info("member.getId() = {}", memberProfileDto == null);

        model.addAttribute("memberForm", new MemberUpdateForm());
        model.addAttribute("memberProfileDto", memberProfileDto);

        return "my-profile";
    }

}
