package custom.capstone.domain.posts.dto;

import custom.capstone.domain.posts.domain.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostListResponseDto {
    private String writer;
    private String title;
    private String content;
    private int price;
    private LocalDateTime createAt;

    public PostListResponseDto(Post post) {
        this.writer = post.getMember().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.createAt = post.getCreateAt();
    }
}