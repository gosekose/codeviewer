package codeview.main.membergroup.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MemberGroupVisibility {
    VISIBLE("visible"),
    HIDDEN("hidden");

    private final String name;
}
