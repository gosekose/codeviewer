package codeview.main.businessservice.school.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class SchoolDto {

    private String name;
    private String address;

    @QueryProjection
    public SchoolDto(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
