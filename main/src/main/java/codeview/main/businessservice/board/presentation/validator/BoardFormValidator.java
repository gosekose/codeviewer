package codeview.main.businessservice.board.presentation.validator;

import codeview.main.businessservice.board.presentation.dao.BoardForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class BoardFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BoardForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardForm boardForm = (BoardForm) target;

        if (!StringUtils.hasText(boardForm.getBoardName())) {
            errors.rejectValue("boardName", "required");
        }

        if (!StringUtils.hasText(boardForm.getBoardMain())) {
            errors.rejectValue("boardMain", "required");
        }

        if (boardForm.getNondisclosure() == null) {
            errors.rejectValue("nondisclosure", "required");
        }

        if (boardForm.getAnonymousCheck() == null) {
            errors.rejectValue("anonymousCheck", "required");
        }
    }
}
