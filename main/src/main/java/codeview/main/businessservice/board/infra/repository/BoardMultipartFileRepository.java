package codeview.main.businessservice.board.infra.repository;

import codeview.main.businessservice.board.domain.BoardMultipartFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMultipartFileRepository extends JpaRepository<BoardMultipartFile, Long> {
}
