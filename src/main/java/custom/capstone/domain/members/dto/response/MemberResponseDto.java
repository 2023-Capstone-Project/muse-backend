package custom.capstone.domain.members.dto.response;

import custom.capstone.domain.members.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberResponseDto {
    private Long id;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String password;

    public MemberResponseDto(final Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
        this.password = member.getPassword();
    }
}
