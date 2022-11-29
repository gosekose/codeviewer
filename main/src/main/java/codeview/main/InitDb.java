package codeview.main;

import codeview.main.businessservice.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        /**
         *
         * member 10명
         * memberGroup 5개
         *
         */
        public void dbInit() {

            for (int i=0; i<10; i++) {

                String uuid = UUID.randomUUID().toString();

                Member member = Member.builder()
                        .email(String.valueOf(i) + "@naver.com")
                        .registrationId("NAVER")
                        .authorities("ROLE_USER")
                        .password(UUID.randomUUID().toString())
                        .registerId(uuid)
                        .build();

                member.updateProfile(UUID.randomUUID().toString(), 20, "studhent", null, null, "iise", UUID.randomUUID().toString());

                em.persist(member);

//                for (int j=0; j<5; j++) {
//
//                    uuid = UUID.randomUUID().toString();
//                    em.persist(MemberGroup.builder()
//                            .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
//                            .name(String.valueOf(i) + "_" + String.valueOf(j))
//                            .password(UUID.randomUUID().toString())
//                            .joinClosedTime(LocalDateTime.now())
//                            .description("")
//                            .maxMember(20)
//                            .creator(member)
//                            .build());
//                }
            }
            em.flush();
        }


//        public void inputSchool() {
//            String schoolAddress;
//
//            em.persist(School.builder().schoolMembership("membership").name("서울과학기술대학교").address("서울특별시 노원구 공릉로 232").build());
//            for(int i=2; i<=10; i ++) {
//                if (i % 6 == 0) {
//                    schoolAddress = "서울특별시";
//                } else if (i % 6 == 1) {
//                    schoolAddress = "부산광역시";
//                } else if (i % 6 == 2) {
//                    schoolAddress = "인천광역시";
//                } else if (i % 6 == 3) {
//                    schoolAddress = "광주광역시";
//                } else if (i % 6 == 4) {
//                    schoolAddress = "울산광역시";
//                } else {
//                    schoolAddress = "대전광역시";
//                }
//                em.persist(School.builder().schoolMembership("membership").name(String.valueOf(i)+"대학교").address(schoolAddress).build());
//            }
//        }

    }
}
