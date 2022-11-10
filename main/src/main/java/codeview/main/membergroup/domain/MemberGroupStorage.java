package codeview.main.membergroup.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @AllArgsConstructor
@NoArgsConstructor
public class MemberGroupStorage extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "classes_storage_id")
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
