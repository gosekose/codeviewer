package codeview.main.businessservice.membergroup.domain.eumerate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MemberGroupVisibility {
    VISIBLE("visible"),
    HIDDEN("hidden");

    private final String name;
}
