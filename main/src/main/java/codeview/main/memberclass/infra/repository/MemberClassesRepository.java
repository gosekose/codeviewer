package codeview.main.memberclass.infra.repository;

import codeview.main.memberclass.domain.MemberClasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberClassesRepository extends JpaRepository<MemberClasses, Long> {

}
