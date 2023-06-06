package custom.capstone.domain.members.dto;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.domain.MemberRole;
import custom.capstone.domain.members.domain.MemberStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponseDto {
    private Long id;
    private String nickname;
    private String email;
    private String phoneNum;
    private String role;
    private String status;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.phoneNum = member.getPhoneNum();
        this.role = member.getRole().toString();
        this.status = member.getStatus().toString();
    }
}
