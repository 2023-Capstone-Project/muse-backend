package custom.capstone.domain.members.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(length = 150, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 50)
    private String email;

    @Column(length = 20, nullable = false)
    private String phoneNum;

    @Enumerated(EnumType.STRING)
    private MemberOccupation occupation;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    public Member(final String name, final String password, final String email, final String phoneNum){
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public Member update(final String name, final String phoneNum) {
        this.name = name;
        this.phoneNum = phoneNum;
        return this;
    }
}
