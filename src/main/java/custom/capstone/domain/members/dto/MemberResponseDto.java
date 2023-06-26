package custom.capstone.domain.members.dto;

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
    private String phoneNum;
    private String role;
    private String status;

    public MemberResponseDto(final Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.phoneNum = member.getPhoneNum();
        this.role = member.getRole().toString();
        this.status = member.getStatus().toString();
    }
}
