package custom.capstone.domain.posts.dto.request;

import custom.capstone.domain.posts.domain.PostType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record PostSaveRequestDto (
        @Length(min = 1, max = 50, message = "제목은 50자 이하로 입력해주세요.")
        String title,

        @NotBlank(message = "내용을 입력해 주세요.")
        String content,

        @NotNull(message = "가격을 입력해 주세요.")
        int price,

        @NotNull(message = "카테고리를 설정해 주세요.")
        String categoryTitle,

        @Enumerated(EnumType.STRING)
        PostType type

        // TODO: image urls
) {
}
