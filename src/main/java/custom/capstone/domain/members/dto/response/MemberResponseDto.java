package custom.capstone.domain.members.dto.response;

import custom.capstone.domain.members.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberResponseDto {
    private String nickname;
    private String profileImage;
    private String email;
    private String phoneNumber;
    private String password;

    public MemberResponseDto(final Member member) {
        this.nickname = member.getNickname();
        this.profileImage = member.getProfileImage();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
        this.password = member.getPassword();
    }
}
