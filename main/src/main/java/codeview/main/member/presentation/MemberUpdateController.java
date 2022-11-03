package codeview.main.member.presentation;

import codeview.main.member.application.MemberService;
import codeview.main.member.application.dto.MemberResponseDto;
import codeview.main.member.application.dto.UpdateMemberRequest;
import codeview.main.member.domain.Member;
import codeview.main.school.application.SchoolService;
import codeview.main.school.application.dto.AllSchoolDto;
import codeview.main.school.application.dto.AllSchoolResult;
import codeview.main.school.application.dto.SchoolDto;
import codeview.main.school.domain.SchoolSearchCondition;
import codeview.main.school.infra.SchoolQueryDslRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberUpdateController {

    private final MemberService memberService;
    private final SchoolService schoolService;
    private final SchoolQueryDslRepositoryImpl schoolQueryDslRepository;

    @GetMapping("/id/update-info/schools")
    public AllSchoolResult allSchoolResult() {

        List<AllSchoolDto> allSchools = schoolService.findAll().stream().map(s -> new AllSchoolDto(s.getName(), s.getAddress()))
                .collect(Collectors.toList());

        return new AllSchoolResult<>(allSchools.size(), allSchools);
    }

    @GetMapping("/id/update-info/specific-schools")
    public Page<SchoolDto> specificSchoolResult(SchoolSearchCondition schoolSearchCondition, Pageable pageable) {

        return schoolQueryDslRepository.searchPageComplex(schoolSearchCondition, pageable);
    }

    @PostMapping("/{id}/profile")
    public MemberResponseDto updateMember(@PathVariable Long id,
                                          @RequestBody UpdateMemberRequest updateMemberRequest) {

        memberService.update(id, updateMemberRequest);
        Member member = memberService.find(id);

        return new MemberResponseDto(member.getId(), member);
    }


    @GetMapping("/profile")
    public String getUpdateMember(Model model) {
        model.addAttribute("memberForm", new UpdateMemberRequest());
        return "my-page";
    }

}
