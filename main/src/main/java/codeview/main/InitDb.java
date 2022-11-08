//package codeview.main;
//
//import codeview.main.school.domain.School;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit();
//    }
//
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final EntityManager em;
//
//        public void dbInit() {
//
//            String schoolAddress;
//
//            em.persist(School.builder().schoolMembership("membership").name("서울과학기술대학교").address("서울특별시 노원구 공릉로 232").build());
//            for(int i=2; i<=100; i ++) {
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
//
//    }
//}
