package codeview.main.member.infra;

import codeview.main.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void 등록이름으로_멤버찾기() throws Exception {
        //given
        for(int i=0; i<100; i++) {
            Member member = createMembers(String.valueOf(i));
            memberRepository.save(member);
        }
        memberRepository.flush();

        //when

        Optional<Member> findMember = memberRepository.findByRegisterId(String.valueOf(5));

        Member member = findMember.orElseThrow(
                () -> {
                    throw new IllegalArgumentException("일치하는 회원이 없습니다.");
                }
        );

        //then
        Assertions.assertThat(member.getRegisterId()).isEqualTo("5");
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