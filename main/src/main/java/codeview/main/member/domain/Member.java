package codeview.main.member.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.common.domain.Address;
import codeview.main.memberclass.domain.MemberClasses;
import codeview.main.memberclass.domain.MemberClassesStorage;
import codeview.main.membership.domain.MemberShip;
import codeview.main.school.domain.School;
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
    private String ci;

    @Column(name = "register_name")
    private String username;
    private String password;
    private String provider;
    private String email;
    private String picture;

    private String authorities;

    private String memberName;
    private Integer age;
    private String work;

    @OneToMany(mappedBy = "member")
    private List<MemberClassesStorage> memberClassesStorageList = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<MemberClasses> memberClassesList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @Embedded
    private Address address;

    private String paymentStatus;

    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    private MemberShip membership;

    private int realPayment;

    @Builder
    public Member(String registrationId, String registerId, String username, String password, String email, String picture, String authorities) {
        this.registrationId = registrationId;
        this.registerId = registerId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.picture = picture;
        this.authorities = authorities;
    }

    public void updateProfile(String memberName, Integer age, String work, School school, Address address) {
        this.memberName = memberName;
        this.age = age;
        this.work = work;
        this.school = school;
        this.address = address;
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
}
