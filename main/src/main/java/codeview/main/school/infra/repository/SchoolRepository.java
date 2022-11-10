package codeview.main.school.infra.repository;

import codeview.main.school.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    @Query("select s from School s where s.name like %:name%")
    List<School> findBySpecificName(@Param("name") String name);

}
