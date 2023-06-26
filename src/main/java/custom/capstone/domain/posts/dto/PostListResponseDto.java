package custom.capstone.domain.posts.dto;

import custom.capstone.domain.posts.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostListResponseDto {
    private String writer;
    private String title;
    private String content;
    private int price;
    private LocalDateTime createAt;

    public PostListResponseDto(final Post post) {
        this.writer = post.getMember().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.createAt = post.getCreatedAt();
    }
}