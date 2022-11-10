package codeview.main.membergroup.presentation.validator;

import codeview.main.membergroup.presentation.dto.CreateGroupForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class GroupDtoValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return CreateGroupForm.class.isAssignableFrom(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        CreateGroupForm createClassesForm = (CreateGroupForm) target;

        if (!StringUtils.hasText(createClassesForm.getName())) {
            errors.rejectValue("name", "required");
        }

        if (createClassesForm.getMaxMember() == null || 10 > createClassesForm.getMaxMember() || createClassesForm.getMaxMember() > 1000000) {
            errors.rejectValue("maxMember","range", new Object[]{10, 1000000}, null);
        }

        if (createClassesForm.getMemberGroupVisibility() == null) {
            errors.rejectValue("memberGroupVisibility", "required");
        }

        if (createClassesForm.getJoinClosedTime() != null) {
            if (createClassesForm.getJoinClosedTime().isBefore(LocalDateTime.now()))
                errors.rejectValue("joinClosedTime", "min", new Object[] {DateTimeFormatter.ofPattern("yyyy-MM-dd hh:MM").format(LocalDateTime.now())}, null);
        }

    }
}
