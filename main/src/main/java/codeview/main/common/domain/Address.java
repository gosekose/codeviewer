package codeview.main.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {

    private String detail;
    private String zipCode;

    @Builder
    public Address(String detail, String zipCode) {
        this.detail = detail;
        this.zipCode = zipCode;
    }
}
