package custom.capstone.domain.members.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record MemberUpdateRequestDto (
        @NotBlank(message = "닉네임을 입력해 주세요.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$",
                message = "닉네임은 특수문자를 제외한 2~10자 이어야 합니다.")
        String nickname,

        @NotBlank(message = "비밀번호를 입력해 주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
                message = "비밀번호는 영문, 숫자 포함 8~16자 이어야 합니다.")
        String password,

        @NotBlank
        String checkPassword,

        @NotBlank(message = "전화번호를 입력해 주세요.")
        @Pattern(regexp = "^01[016789]-\\d{4}-\\d{4}$",
                message = "핸드폰 양식과 맞지 않습니다.(01x-xxxx-xxxx)")
        String phoneNumber

        //TODO: profile Image Url
) {
}
