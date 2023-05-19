package custom.capstone.domain.members.domain;

import custom.capstone.domain.posts.domain.InterestList;
import custom.capstone.domain.posts.domain.Post;

import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(length = 20, unique = true, nullable = false)
    private String nickname;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String phoneNum;

    @Enumerated(EnumType.STRING)
    private MemberRole occupation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private final List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private final List<InterestList> interestList = new ArrayList<>();

    /**
     * 정적 생성자
     */
    @Builder
    public Member(String nickname, String password, String email, String phoneNum, MemberRole occupation) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
        this.occupation = occupation;
    }

    public void update(String nickname, String password, String email, String phoneNum) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
    }
}
