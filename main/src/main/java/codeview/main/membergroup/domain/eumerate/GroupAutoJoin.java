package codeview.main.membergroup.domain.eumerate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GroupAutoJoin {
    ON("설정"),
    OFF("해제");

    private final String name;
}
