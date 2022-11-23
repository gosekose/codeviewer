package codeview.main.membergroup.domain;

import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.eumerate.GroupJoinStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter @NoArgsConstructor
public class GroupJoinRequest {

    @Id @GeneratedValue
    @Column(name = "join_request_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_group_id")
    private MemberGroup memberGroup;

    @Enumerated(EnumType.STRING)
    private GroupJoinStatus groupJoinStatus;

    private int denialCount;

    @Builder
    public GroupJoinRequest(Member member, MemberGroup memberGroup, GroupJoinStatus groupJoinStatus, int denialCount) {
        this.member = member;
        this.memberGroup = memberGroup;
        this.groupJoinStatus = groupJoinStatus;
        this.denialCount = denialCount;
    }

    public void updateGroupStatus(GroupJoinStatus groupJoinStatus) {

        this.groupJoinStatus = groupJoinStatus;
        this.denialCount++;
    }

}
