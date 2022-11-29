package codeview.main.businessservice.member.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto<T> {

    private Long id;
    private T data;
}
