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
    private Long categoryId;
    private String title;
    private String writer;
    private int price;
    private int views;
    private int interestCount;
    private PostType type;
    private LocalDateTime createdAt;
    private String thumbnailUrl;

    public PostListResponseDto(final Post post, final String thumbnailUrl) {
        this.postId = post.getId();
        this.categoryId = post.getCategory().getId();
        this.title = post.getTitle();
        this.writer = post.getMember().getNickname();
        this.price = post.getPrice();
        this.type = post.getType();
        this.views = post.getViews();
        this.interestCount = post.getInterestCount();
        this.createdAt = post.getCreatedAt();
        this.thumbnailUrl = thumbnailUrl;
    }
}