package codeview.main.businessservice.school.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpecificSchoolResult<T> {

    private int count;
    private T data;

}
