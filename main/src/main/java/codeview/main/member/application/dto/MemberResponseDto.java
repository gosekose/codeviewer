package codeview.main.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto<T> {

    private Long id;
    private T data;
}
