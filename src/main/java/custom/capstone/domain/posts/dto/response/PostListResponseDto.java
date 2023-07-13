package custom.capstone.domain.posts.dto.response;

import custom.capstone.domain.posts.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class PostListResponseDto {
    private String writer;
    private String title;
    private String content;
//    private String previewImgUrl; -> TODO: 이미지 구현 후 적용하기

    public PostListResponseDto(final Post post) {
        this.writer = post.getMember().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
//        this.previewImgUrl = previewImgUrl;
    }
}