package codeview.main.member.application;

import codeview.main.businessservice.member.application.MemberService;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.member.infra.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void beforeAll() {

        for(int i=0; i<10; i++) {
            Member member = createMembers(String.valueOf(i));
            memberRepository.save(member);
        }
        memberRepository.flush();
    }


    @Test
    public void 등록이름으로_멤버찾기() throws Exception {

        //when
        Member member = memberService.findByRegisterId(String.valueOf(5));

        //then
        Assertions.assertThat(member.getRegisterId()).isEqualTo("5");

    }


    @Test
    public void 멤버찾기_에러() throws Exception {

        //then

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> { memberService.findByRegisterId(String.valueOf(11));}
        );
    }

    protected Member createMembers() {

        return Member.builder()
                .authorities("MEMBER")
                .email(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString())
                .picture(UUID.randomUUID().toString())
                .registerId(UUID.randomUUID().toString())
                .build();

    }

    protected Member createMembers(String regisId) {

        return Member.builder()
                .authorities("MEMBER")
                .email(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString())
                .picture(UUID.randomUUID().toString())
                .registerId(regisId)
                .build();

    }

}