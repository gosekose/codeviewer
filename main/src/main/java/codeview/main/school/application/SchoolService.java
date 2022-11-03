package codeview.main.school.application;

import codeview.main.school.domain.School;
import codeview.main.school.infra.SchoolRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public List<School> findAll() {
        List<School> schools = schoolRepository.findAll();

        if (schools == null) {
            throw new IllegalArgumentException("제휴 학교가 없습니다.");
        }

        return schools;
    }

    public List<School> findBySpecificName(String schoolName) {
        List<School> schools = schoolRepository.findBySpecificName(schoolName);

        if (schools == null) {
            throw new IllegalArgumentException("학교를 찾을 수 없습니다.");
        }

        return schools;
    }

}
