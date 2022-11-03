package codeview.main.school.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AllSchoolResult<T> {

    private int count;
    private T data;

}

