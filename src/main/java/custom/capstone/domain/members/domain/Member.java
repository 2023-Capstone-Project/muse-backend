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

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private final List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private final List<InterestList> interestList = new ArrayList<>();

    /**
     * 생성자 로직
     */
    @Builder
    public Member(final String name, final String password, final String email, final String phoneNum){
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    /**
     * 수정 로직
     */
    public void updateName(final String name) {
        this.name = name;
    }

    public void updatePassword(final String password) {
        this.password = password;
    }

    public void updateEmail(final String email) {
        this.email = email;
    }

    public void updatePhoneNum(final String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
