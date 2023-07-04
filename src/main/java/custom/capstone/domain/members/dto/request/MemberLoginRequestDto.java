package custom.capstone.domain.members.dto.request;

import javax.validation.constraints.Email;

public record MemberLoginRequestDto(
        @Email(message = "이메일 형식에 맞지 않습니다.")
        String email,

//        @Pattern(regexp = "^[A-Za-z[0-9]]{8,16}$",
//                message = "비밀번호는 영문, 숫자 포함 8~16자 이어야 합니다.")
        String password
) {
}
