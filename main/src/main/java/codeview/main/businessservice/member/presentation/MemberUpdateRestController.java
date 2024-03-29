package codeview.main.businessservice.member.presentation;

import codeview.main.businessservice.member.application.MemberService;
import codeview.main.businessservice.school.application.SchoolService;
import codeview.main.businessservice.school.infra.dao.SchoolSearchCondition;
import codeview.main.businessservice.school.infra.repository.SchoolQueryDslRepositoryImpl;
import codeview.main.businessservice.school.presentation.dto.AllSchoolDto;
import codeview.main.businessservice.school.presentation.dto.AllSchoolResult;
import codeview.main.businessservice.school.presentation.dto.SchoolDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/members")
@RequiredArgsConstructor
public class MemberUpdateRestController {

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

//    @PostMapping("/{id}/update-info")
//    public MemberResponseDto updateMember(@PathVariable("id") Long id,
//                                          @RequestBody MemberUpdateForm updateMemberRequest) {
//        memberService.update(id, updateMemberRequest);
//        Member member = memberService.find(id);
//
//        return new MemberResponseDto(member.getId(), member);
//    }

}
