package codeview.main.memberclass.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MemberClassesVisibility {
    VISIBLE("visible"),
    HIDDEN("hidden");

    private final String name;
}
