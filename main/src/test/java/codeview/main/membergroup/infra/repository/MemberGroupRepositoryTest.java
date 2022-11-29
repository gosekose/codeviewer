package codeview.main.membergroup.infra.repository;

import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.businessservice.membergroup.infra.repository.membergroup.MemberGroupRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
class MemberGroupRepositoryTest {

    @Autowired
    private MemberGroupRepository memberGroupRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void 그룹저장() throws Exception {
        //given

        for(int i = 0; i<100; i++) {

            Member member = createMembers();
            em.persist(member);

            MemberGroup memberGroup = MemberGroup.builder()
                    .creator(member)
                    .name(UUID.randomUUID().toString())
                    .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                    .joinClosedTime(LocalDateTime.now())
                    .description(UUID.randomUUID().toString())
                    .password(UUID.randomUUID().toString())
                    .build();

            em.persist(memberGroup);
        }

        em.flush();

        //when
        List<MemberGroup> memberGroups = memberGroupRepository.findAll();

//        //then
        Assertions.assertThat(memberGroups.size()).isEqualTo(100);
    }

    @Test
    public void 멤버로_그룹찾기() throws Exception {
        //given
        Member member = createMembers("1");
        em.persist(member);

        Member member2 = createMembers("2");
        em.persist(member2);

        //when

        for(int i=0; i<40; i++) {
            memberGroupRepository.save(MemberGroup.builder()
                    .creator(member)
                    .name(String.valueOf(i))
                    .maxMember(30)
                    .description("tttt")
                    .build());
        }

        for(int i=0; i<10; i++) {
            memberGroupRepository.save(MemberGroup.builder()
                    .creator(member2)
                    .name(String.valueOf(i))
                    .maxMember(30)
                    .description("tttt")
                    .build());
        }

        List<MemberGroup> members = memberGroupRepository.findAllByMember(member);
        List<MemberGroup> members2 = memberGroupRepository.findAllByMember(member2);

        //then
        Assertions.assertThat(members.size()).isEqualTo(40);
        Assertions.assertThat(members2.size()).isEqualTo(10);

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

    protected Member createMembers(String registerId) {

        return Member.builder()
                .authorities("MEMBER")
                .email(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString())
                .picture(UUID.randomUUID().toString())
                .registerId(registerId)
                .build();

    }


}