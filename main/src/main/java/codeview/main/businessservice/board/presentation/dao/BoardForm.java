package codeview.main.businessservice.board.presentation.dao;

import codeview.main.businessservice.board.domain.enumtype.AnonymousCheck;
import codeview.main.businessservice.board.domain.enumtype.Nondisclosure;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BoardForm {

    @NotBlank(message = "질문 제목은 필수 입니다.")
    @Length(min = 4, max = 100, message = "제목 길이는 4 ~ 100 사이입니다.")
    private String boardName;

    @NotBlank(message = "질문 내용은 필수 입니다.")
    @Length(min = 10, message = "질문 내용은 10자 이상 입니다.")
    private String boardMain;

    @NotNull(message = "익명 여부는 필수 입니다.")
    private AnonymousCheck anonymousCheck;

    @NotNull(message = "공개 여부는 필수 입니다.")
    private Nondisclosure nondisclosure;

    private List<MultipartFile> boardMultipartFileList;

    private Long problemId;

}
