package codeview.main.membergroup.domain.eumerate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GroupJoinStatus {
    WAIT("대기"),
    JOIN("승인"),
    NOTJOIN("미신청");


    private final String name;
}
