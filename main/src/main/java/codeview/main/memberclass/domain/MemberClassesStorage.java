package codeview.main.memberclass.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @AllArgsConstructor
@NoArgsConstructor
public class MemberClassesStorage extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "classes_storage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_classes_id")
    private MemberClasses memberClasses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private MemberClassesAuthority memberClassesAuthority;


}
