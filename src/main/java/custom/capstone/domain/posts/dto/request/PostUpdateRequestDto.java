package custom.capstone.domain.posts.dto.request;

import custom.capstone.domain.posts.domain.PostStatus;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public record PostUpdateRequestDto (
        @Length(min = 1, max = 50, message = "제목은 50자 이하로 입력해주세요.")
        String title,

        @NotBlank(message = "내용을 입력해 주세요.")
        String content,

        @NotBlank(message = "가격을 입력해 주세요.")
        int price,

        @NotBlank(message = "게시글 상태를 선택해 주세요.")
        PostStatus status
) {
}
