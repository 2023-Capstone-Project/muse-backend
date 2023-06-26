package custom.capstone.domain.members.domain;

import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.review.domain.Review;
import custom.capstone.domain.trading.domain.Trading;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY)
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
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    @Column(name = "profile_image")
    private String profileImage;

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private final List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private final List<Interest> interests = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private final List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "buyer", cascade = REMOVE)
    private final List<Trading> buyerTradings = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = REMOVE)
    private final List<Trading> sellerTradings = new ArrayList<>();

    @Builder
    public Member(
            final String nickname,
            final String password,
            final String email,
            final String phoneNum,
            final MemberRole role,
            final MemberStatus status) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
        this.role = role;
        this.status = status;
    }

    public void update(
            final String nickname,
            final String password,
            final String email,
            final String phoneNum) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public void updateProfileImage(final String profileImage) {
        this.profileImage = profileImage;
    }
}
