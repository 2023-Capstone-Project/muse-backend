package custom.capstone.domain.members.domain;

import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.magazine.domain.Magazine;
import custom.capstone.domain.notice.domain.Notice;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.review.domain.Review;
import custom.capstone.domain.trading.domain.Trading;
import custom.capstone.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseTimeEntity implements UserDetails {
    @Id @GeneratedValue(strategy = IDENTITY)
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

    private String profileImage;

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private final List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private final List<Magazine> magazines = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private final List<Notice> notices = new ArrayList<>();

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

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    public void updateProfileImage(final String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("member"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
