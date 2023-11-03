package custom.capstone.domain.posts.dto.response;

import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.domain.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    // TODO: List<Image> -> 변수명은 thumImageUrl 로 바꾸기
    private List<String> imageUrls;

    public PostListResponseDto(final Post post) {
        this.postId = post.getId();
        this.writer = post.getMember().getNickname();
        this.title = post.getTitle();
        this.price = post.getPrice();
        this.type = post.getType();
        this.createAt = post.getCreatedAt();
        this.imageUrls = post.getPostImages().stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
    }
}