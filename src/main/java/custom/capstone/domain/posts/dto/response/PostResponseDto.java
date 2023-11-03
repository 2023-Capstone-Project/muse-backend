package custom.capstone.domain.posts.dto.response;

import custom.capstone.domain.members.dto.MemberProfileDto;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.domain.PostType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private int views;
    private int interestCount;
    private List<String> imageUrls;
    private String categoryTitle;
    private PostType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MemberProfileDto writer;

    public PostResponseDto(final Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.views = post.getViews();
        this.interestCount = post.getInterestCount();
        this.categoryTitle = post.getCategory().getTitle();
        this.type = post.getType();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.writer = new MemberProfileDto(
                post.getId(),
                post.getMember().getNickname()
        );
        this.imageUrls = post.getPostImages().stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
    }
}
