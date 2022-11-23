package codeview.main.membergroup.domain.eumerate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GroupJoinStatus {
    WAIT("WAIT"),
    JOIN("JOIN"),
    NOTJOIN("NOTJOIN");


    private final String name;
}
