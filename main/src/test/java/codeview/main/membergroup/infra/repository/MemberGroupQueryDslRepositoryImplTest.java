//package codeview.main.membergroup.infra.repository;
//
//import codeview.main.member.application.MemberService;
//import codeview.main.member.domain.Member;
//import codeview.main.member.infra.MemberRepository;
//import codeview.main.membergroup.domain.MemberGroup;
//import codeview.main.membergroup.domain.eumerate.MemberGroupVisibility;
//import codeview.main.membergroup.presentation.dao.MemberGroupSearchCondition;
//import codeview.main.membergroup.presentation.dto.GroupForPageDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//class MemberGroupQueryDslRepositoryImplTest {
//
//    @Autowired
//    private MemberGroupQueryDslRepositoryImpl memberGroupQueryDslRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private MemberGroupRepository memberGroupRepository;
//
//    @Autowired
//    private MemberService memberService;
//
//    @BeforeEach
//    public void dataInit() {
//        for(int i=0; i<10; i++) {
//            Member member = Member.builder()
//                    .registrationId("NAVER")
//                    .authorities("MEMBER")
//                    .registerId(String.valueOf(i))
//                    .picture(UUID.randomUUID().toString())
//                    .password(UUID.randomUUID().toString())
//                    .email(UUID.randomUUID().toString())
//                    .build();
//
//            memberRepository.save(member);
//        }
//
//        Member member = Member.builder()
//                .registerId(String.valueOf(10))
//                .picture(UUID.randomUUID().toString())
//                .password(UUID.randomUUID().toString())
//                .email(UUID.randomUUID().toString())
//                .build();
//
//        memberRepository.save(member);
//
//        for(int i=0; i<100; i++) {
//
//            if (i % 4 == 0) {
//
//                memberGroupRepository.save(
//                        MemberGroup.builder().member(member)
//                                .maxMember(20)
//                                .description(String.valueOf(i))
//                                .joinClosedTime(LocalDateTime.now())
//                                .name("TEST1")
//                                .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
//                                .build());
//
//            } else if (i % 4 == 1) {
//
//                memberGroupRepository.save(MemberGroup.builder().member(member)
//                        .maxMember(20)
//                        .description(String.valueOf(i))
//                        .joinClosedTime(LocalDateTime.now())
//                        .name("TEST1")
//                        .memberGroupVisibility(MemberGroupVisibility.HIDDEN)
//                        .build());
//
//            } else if (i % 4 == 2) {
//
//                memberGroupRepository.save(MemberGroup.builder().member(member)
//                        .maxMember(20)
//                        .description(String.valueOf(i))
//                        .joinClosedTime(LocalDateTime.now())
//                        .name("TEST2")
//                        .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
//                        .build());
//
//            } else {
//
//                memberGroupRepository.save(MemberGroup.builder().member(member)
//                        .maxMember(20)
//                        .description(String.valueOf(i))
//                        .joinClosedTime(LocalDateTime.now())
//                        .name("TEST2")
//                        .memberGroupVisibility(MemberGroupVisibility.HIDDEN)
//                        .build());
//
//            }
//
//        }
//    }
//
//    @Test
//    public void 이름으로_조회() throws Exception {
//
//        /*
//         * 동적 쿼리 검증
//         * 그룹 호스트, 그룹명, 그룹 공개 여부로 동적 쿼리 생성
//         */
//
//        //given
//        MemberGroupSearchCondition condition1 = new MemberGroupSearchCondition(null,"TEST1", null);
//        MemberGroupSearchCondition condition2 = new MemberGroupSearchCondition(null, "TEST1", MemberGroupVisibility.VISIBLE);
//
//        MemberGroupSearchCondition condition3 = new MemberGroupSearchCondition(null, "TEST2", MemberGroupVisibility.HIDDEN);
//        MemberGroupSearchCondition condition4 = new MemberGroupSearchCondition(null, "TEST2", null);
//
//        //when
//        List<MemberGroup> search1 = memberGroupQueryDslRepository.search(condition1);
//        List<MemberGroup> search2 = memberGroupQueryDslRepository.search(condition2);
//        List<MemberGroup> search3 = memberGroupQueryDslRepository.search(condition3);
//        List<MemberGroup> search4 = memberGroupQueryDslRepository.search(condition4);
//
//
//        //then
//        assertThat(search1.size()).isEqualTo(50);
//        assertThat(search2.size()).isEqualTo(25);
//        assertThat(search3.size()).isEqualTo(25);
//        assertThat(search4.size()).isEqualTo(50);
//
//    }
//
//    @Test
//    public void 공개여부_조회() throws Exception {
//        //given
//
//        MemberGroupSearchCondition condition5 = new MemberGroupSearchCondition(null, null, MemberGroupVisibility.VISIBLE);
//        MemberGroupSearchCondition condition6 = new MemberGroupSearchCondition(null, null, MemberGroupVisibility.HIDDEN);
//
//        //when
//
//        List<MemberGroup> search5 = memberGroupQueryDslRepository.search(condition5);
//        List<MemberGroup> search6 = memberGroupQueryDslRepository.search(condition6);
//
//        //then
//
//        assertThat(search5.size()).isEqualTo(50);
//        assertThat(search6.size()).isEqualTo(50);
//
//    }
//
//    @Test
//    public void 전체_or_일부_이름으로_조회() throws Exception {
//        //given
//        MemberGroupSearchCondition condition7 = new MemberGroupSearchCondition(null, null, null);
//        MemberGroupSearchCondition condition8 = new MemberGroupSearchCondition(null, "TES", null);
//
//        //when
//        List<MemberGroup> search7 = memberGroupQueryDslRepository.search(condition7);
//        List<MemberGroup> search8 = memberGroupQueryDslRepository.search(condition8);
//
//        //then
//        assertThat(search7.size()).isEqualTo(100);
//        assertThat(search8.size()).isEqualTo(100);
//
//    }
//
//
//
//    @Test
//    public void 마이클래스_전체조회() throws Exception {
//        //given
//        Member member = memberService.findByRegisterId(String.valueOf(10));
//
//        //when
//        MemberGroupSearchCondition condition1 = new MemberGroupSearchCondition(member,"TEST1", null);
//        List<MemberGroup> search1 = memberGroupQueryDslRepository.search(condition1);
//
//        //then
//        assertThat(search1.size()).isEqualTo(50);
//
//    }
//
//    @Test
//    public void 페이징_멤버그룹_리스트_사이즈() throws Exception {
//
//        //given
//        Member member = memberService.findByRegisterId(String.valueOf(10));
//        Pageable pageable = PageRequest.of(0, 20);
//
//        //when
//        MemberGroupSearchCondition condition1 = new MemberGroupSearchCondition(member,"TEST1", null);
//        Page<GroupForPageDto> memberGroupDtos = memberGroupQueryDslRepository.searchPageComplex(condition1, pageable);
//
//        List<GroupForPageDto> content = memberGroupDtos.getContent();
//
//        //then
//        assertThat(content.size()).isEqualTo(20);
//    }
//
//    @Test
//    public void 페이징_정보처리() throws Exception {
//
//        /**
//         * 페이징 정렬은 클라인언트에서 진행 함
//         * 페이징 그대로 클라이언트에게 전달하여 처리
//         */
//
//        //given
//        Member member = memberService.findByRegisterId(String.valueOf(10));
//        Pageable pageable = PageRequest.of(0, 20);
//        //when
//
//        MemberGroupSearchCondition condition = new MemberGroupSearchCondition(member, "TEST1", null);
//        Page<GroupForPageDto> memberGroupDtos = memberGroupQueryDslRepository.searchPageComplex(condition, pageable);
//
//        assertThat(memberGroupDtos.getSize()).isEqualTo(20);
//        assertThat(memberGroupDtos.getTotalPages()).isEqualTo(3);
//        assertThat(memberGroupDtos.getTotalElements()).isEqualTo(50);
//    }
//}