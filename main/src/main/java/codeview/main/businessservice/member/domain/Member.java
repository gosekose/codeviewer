package codeview.main.businessservice.member.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.businessservice.groupstorage.domain.GroupStorage;
import codeview.main.businessservice.membergroup.domain.GroupJoinRequest;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.membership.domain.MemberShip;
import codeview.main.businessservice.school.domain.School;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String registrationId;

    private String registerId;

    private String password;
    private String email;
    private String picture;

    private String authorities;

    private String memberName;
    private Integer age;
    private String work;

    @OneToMany(mappedBy = "member")
    private List<GroupStorage> groupStorageList = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<MemberGroup> memberGroupList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;


    private String department;
    private String privateIdInSchool;

    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    private MemberShip membership;

    private int realPayment;

    @OneToMany(mappedBy = "member")
    private List<GroupJoinRequest> groupJoinRequests = new ArrayList<>();

    private String userComment;

    private String facebookEmail;
    private String linkedInEmail;
    private String githubEmail;

    @Builder
    public Member(String registrationId, String registerId, String password, String email, String picture, String authorities) {
        this.registrationId = registrationId;
        this.registerId = registerId;
        this.password = password;
        this.email = email;
        this.picture = picture;
        this.authorities = authorities;
    }

    public void updateProfile(String memberName, Integer age, String work, School school,
                              String department, String privateIdInSchool) {
        this.memberName = memberName;
        this.age = age;
        this.work = work;
        this.school = school;
        this.department = department;
        this.privateIdInSchool = privateIdInSchool;
    }

    public void updateMembership(MemberShip membership) {
        this.membership = membership;
        this.realPayment = membership.getPayment();
    }

    public void updatePaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void minusRealPayment(int minusPayment) {

        if (realPayment - realPayment >= 0 ) {
            this.realPayment -= realPayment;
        }
    }

    public void updateSchool(School school) { this.school = school; }
}
