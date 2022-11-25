package codeview.main.membergroup.domain.eumerate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GroupJoinStatus {
    WAIT("WAIT"),
    ALREADY("ALREADY"),
    JOIN("JOIN"),

    ONEDELETE("ONEDELTE"),
    NOTJOIN("NOTJOIN");


    private final String name;
}
