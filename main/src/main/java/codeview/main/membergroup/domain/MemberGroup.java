package codeview.main.membergroup.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity @AllArgsConstructor
@NoArgsConstructor
public class MemberGroup extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_classes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member creator;

    @OneToMany(mappedBy = "memberGroup")
    private List<MemberGroupStorage> memberGroupStorage = new ArrayList<>();

    private String name;
    private Integer maxMember;

    @Enumerated(EnumType.STRING)
    private MemberGroupVisibility memberGroupVisibility;

    private LocalDateTime joinClosedTime;

    private String description;

    private String skillTag;

    private String password;

    @Builder
    public MemberGroup(Member member, String name, Integer maxMember, MemberGroupVisibility memberGroupVisibility, LocalDateTime joinClosedTime, String description, String skillTag, String password) {
        this.creator = member;
        this.name = name;
        this.maxMember = maxMember;
        this.memberGroupVisibility = memberGroupVisibility;
        this.joinClosedTime = joinClosedTime;
        this.description = description;
        this.skillTag = skillTag;
        this.password = password;
    }
}
