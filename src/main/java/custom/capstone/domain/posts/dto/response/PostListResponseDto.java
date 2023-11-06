package custom.capstone.domain.posts.dto.response;

import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.domain.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class PostListResponseDto {
    private Long postId;
    private String writer;
    private String title;
    private int price;
    private PostType type;
    private LocalDateTime createAt;
    private String thumbnailUrl;

    public PostListResponseDto(final Post post, final String thumbnailUrl) {
        this.postId = post.getId();
        this.writer = post.getMember().getNickname();
        this.title = post.getTitle();
        this.price = post.getPrice();
        this.type = post.getType();
        this.createAt = post.getCreatedAt();
        this.thumbnailUrl = thumbnailUrl;
    }
}