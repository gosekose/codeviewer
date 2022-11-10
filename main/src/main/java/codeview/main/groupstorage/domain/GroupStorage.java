package codeview.main.groupstorage.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.MemberGroupAuthority;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @AllArgsConstructor
@NoArgsConstructor
public class GroupStorage extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "group_storage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_classes_id")
    private MemberGroup memberGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private MemberGroupAuthority memberGroupAuthority;


}
