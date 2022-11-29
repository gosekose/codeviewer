package codeview.main.businessservice.membergroup.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.groupstorage.domain.GroupStorage;
import codeview.main.businessservice.membergroup.domain.eumerate.GroupAutoJoin;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.businessservice.problem.domain.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;


@Getter
@Entity @AllArgsConstructor
@NoArgsConstructor
public class MemberGroup extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member creator;

    @OneToMany(mappedBy = "memberGroup")
    private List<GroupStorage> groupStorage = new ArrayList<>();

    @OneToMany(mappedBy = "memberGroup")
    private List<Problem> problems = new ArrayList<>();

    private String name;
    private Integer maxMember;

    @Enumerated(STRING)
    private MemberGroupVisibility memberGroupVisibility;

    private LocalDateTime joinClosedTime;

    private String description;

    private String skillTag;

    private String password;

    @Enumerated(STRING)
    private GroupAutoJoin groupAutoJoin;

    @OneToMany(mappedBy = "memberGroup")
    private List<GroupJoinRequest> groupJoinRequests = new ArrayList<>();

    @Builder
    public MemberGroup(Member creator, String name, Integer maxMember, MemberGroupVisibility memberGroupVisibility,
                       LocalDateTime joinClosedTime, String description, String skillTag, String password,
                       GroupAutoJoin groupAutoJoin) {
        this.creator = creator;
        this.name = name;
        this.maxMember = maxMember;
        this.memberGroupVisibility = memberGroupVisibility;
        this.joinClosedTime = joinClosedTime;
        this.description = description;
        this.skillTag = skillTag;
        this.password = password;
        this.groupAutoJoin = groupAutoJoin;
    }
}
