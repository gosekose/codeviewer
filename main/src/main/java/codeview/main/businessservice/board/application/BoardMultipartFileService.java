package codeview.main.businessservice.board.application;

import codeview.main.businessservice.board.domain.Board;
import codeview.main.businessservice.board.domain.BoardMultipartFile;
import codeview.main.businessservice.board.infra.repository.BoardMultipartFileRepository;
import codeview.main.businessservice.board.presentation.dao.BoardForm;
import codeview.main.common.domain.UploadFile;
import codeview.main.businessservice.problem.infra.util.filestore.CommonFilStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardMultipartFileService {

    private final BoardMultipartFileRepository boardMultipartFileRepository;
    private final CommonFilStore commonFilStore;

    @Transactional
    public void saveBoardMultipartFile(Long groupId, BoardForm boardForm, Board board) throws IOException {

        if (boardForm.getBoardMultipartFileList() != null) {

            for (MultipartFile multipartFile : boardForm.getBoardMultipartFileList()) {
                UploadFile uploadFile = commonFilStore.makeStoreFolder(multipartFile, String.valueOf(groupId), UUID.randomUUID().toString());

                BoardMultipartFile boardMultipartFile = BoardMultipartFile.builder()
                        .board(board)
                        .boardFile(uploadFile)
                        .build();

                boardMultipartFileRepository.save(boardMultipartFile);
                board.addBoardMultipartFiles(boardMultipartFile);
            }
        }
    }

}
