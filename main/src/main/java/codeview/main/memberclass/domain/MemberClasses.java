package codeview.main.memberclass.domain;

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
public class MemberClasses extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_classes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member creator;

    @OneToMany(mappedBy = "memberClasses")
    private List<MemberClassesStorage> memberClassesStorage = new ArrayList<>();

    private String className;
    private Integer maxMember;

    @Enumerated(EnumType.STRING)
    private MemberClassesVisibility memberClassesVisibility;

    private LocalDateTime joinClosedTime;

    private String description;

    private String skillTag;

    private String password;

    @Builder
    public MemberClasses(Member member, String className, Integer maxMember, MemberClassesVisibility memberClassesVisibility, LocalDateTime joinClosedTime, String description, String skillTag, String password) {
        this.creator = member;
        this.className = className;
        this.maxMember = maxMember;
        this.memberClassesVisibility = memberClassesVisibility;
        this.joinClosedTime = joinClosedTime;
        this.description = description;
        this.skillTag = skillTag;
        this.password = password;
    }
}