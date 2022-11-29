package codeview.main.businessservice.membership.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberShip {

    PROPLUS("proplus", 9900),
    PRO("pro", 7000),
    GUEST("guest", 0);

    private final String membershipName;
    private final int payment;

}
