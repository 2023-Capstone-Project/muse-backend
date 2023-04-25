package custom.capstone.domain.members.domain;

import custom.capstone.domain.members.dto.UpdateMemberDto;
import custom.capstone.domain.posts.domain.InterestList;
import custom.capstone.domain.posts.domain.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 150, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String phoneNum;

    @Enumerated(EnumType.STRING)
    private MemberOccupation occupation;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private final List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private final List<InterestList> interestList = new ArrayList<>();

    /**
     * 생성자 로직
     */
    public static Member of(
            String name,
            String password,
            String email,
            String phoneNum,
            MemberOccupation occupation,
            MemberStatus status
    ) {
        Member member = new Member();
        member.name = name;
        member.password = password;
        member.email = email;
        member.phoneNum = phoneNum;
        member.occupation = occupation;
        member.status = status;

        return member;
    }

    /**
     * 수정 로직
     */
    public void update(UpdateMemberDto memberDto) {
        this.name = memberDto.name();
        this.password = memberDto.password();
        this.email = memberDto.email();
        this.phoneNum = memberDto.phoneNum();
    }
}
