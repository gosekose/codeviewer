package codeview.main.groupstorage.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.eumerate.MemberGroupAuthority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupStorage extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "group_storage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_group_id")
    private MemberGroup memberGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private MemberGroupAuthority memberGroupAuthority;

    @Builder
    public GroupStorage(MemberGroup memberGroup, Member member, MemberGroupAuthority memberGroupAuthority) {
        this.memberGroup = memberGroup;
        this.member = member;
        this.memberGroupAuthority = memberGroupAuthority;
    }
}
