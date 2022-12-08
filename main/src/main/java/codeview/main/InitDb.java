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

            for (int i=0; i<5; i++) {

                String uuid = UUID.randomUUID().toString();

                Member member = Member.builder()
                        .email(i + "@naver.com")
                        .registrationId("NAVER")
                        .authorities("ROLE_USER")
                        .password(UUID.randomUUID().toString())
                        .registerId(uuid)
                        .build();

                member.updateProfile(UUID.randomUUID().toString(), 20, "student", null, "iise", UUID.randomUUID().toString());

                em.persist(member);
            }
            em.flush();
        }
    }
}
