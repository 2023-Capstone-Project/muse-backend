package custom.capstone.domain.members.dto;

import javax.validation.constraints.NotEmpty;

public record SignUpMemberDto(
        @NotEmpty(message = "이름을 입력해 주세요.")
        String name,
        @NotEmpty(message = "비밀번호를 입력해 주세요.")
        String password,
        @NotEmpty(message = "이메일을 입력해 주세요.")
        String email,
        @NotEmpty(message = "전화번호를 입력해 주세요.")
        String phoneNum
) {
}
