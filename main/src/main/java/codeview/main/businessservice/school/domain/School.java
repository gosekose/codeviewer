package codeview.main.businessservice.school.domain;

import codeview.main.businessservice.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class School {

    @Id @GeneratedValue
    @Column(name = "school_id")
    private Long id;
    private String schoolMembership;
    private String name;

    private String address;

    @OneToMany(mappedBy = "school")
    private List<Member> members = new ArrayList<>();

    @Builder
    public School(String schoolMembership, String name, String address) {
        this.schoolMembership = schoolMembership;
        this.name = name;
        this.address = address;
    }
}
