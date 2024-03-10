package custom.capstone.domain.posts.dto.response;

import custom.capstone.domain.members.dto.MemberProfileDto;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.domain.PostType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostResponseDto {
    private Long postId;
    private Long categoryId;
    private String title;
    private String content;
    private int price;
    private int views;
    private int interestCount;
    private String categoryTitle;
    private PostType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MemberProfileDto writer;
    private List<String> imageUrls;

    public PostResponseDto(final Post post, final List<String> imageUrls) {
        this.postId = post.getId();
        this.categoryId = post.getCategory().getId();
        this.categoryTitle = post.getCategory().getTitle();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.views = post.getViews();
        this.interestCount = post.getInterestCount();
        this.type = post.getType();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.writer = new MemberProfileDto(
                post.getMember().getId(),
                post.getMember().getNickname(),
                post.getMember().getProfileImage()
        );
        this.imageUrls = imageUrls;
    }
}
