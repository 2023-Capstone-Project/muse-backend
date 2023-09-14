package custom.capstone.domain.posts.dto.response;

import custom.capstone.domain.members.dto.MemberProfileDto;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.domain.PostType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private int views;
    private int interestCount;
    //    private List<String> imgUrls; -> TODO: 이미지 구현 후 적용하기
    private PostType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MemberProfileDto writer;

    public PostResponseDto(final Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.views = post.getViews();
        this.interestCount = post.getInterestCount();
//        this.imgUrls = imgUrls;
        this.type = post.getType();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.writer = new MemberProfileDto(post.getMember().getId(), post.getMember().getNickname());
    }
}
