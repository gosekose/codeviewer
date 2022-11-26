package codeview.main.board.presentation.dao;

import codeview.main.board.domain.enumtype.AnonymousCheck;
import codeview.main.board.domain.enumtype.Nondisclosure;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoardForm {

    private String boardName;
    private String boardMain;
    private AnonymousCheck anonymousCheck;
    private Nondisclosure nondisclosure;
    private List<MultipartFile> boardMultipartFileList = new ArrayList<>();
    private Integer problemId;

}
