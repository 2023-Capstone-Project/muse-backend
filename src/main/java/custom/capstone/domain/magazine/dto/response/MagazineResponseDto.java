package custom.capstone.domain.magazine.dto.response;

import custom.capstone.domain.magazine.domain.Magazine;
import custom.capstone.domain.members.dto.MemberProfileDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MagazineResponseDto {
    private Long magazineId;
    private String title;
    private MemberProfileDto admin;
    private String content;
    private int views;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> imageUrls;

    public MagazineResponseDto(final Magazine magazine, final List<String> imageUrls) {
        this.magazineId = magazine.getId();
        this.title = magazine.getTitle();
        this.admin = new MemberProfileDto(
                magazine.getMember().getId(),
                magazine.getMember().getNickname(),
                magazine.getMember().getProfileImage()
        );
        this.content = magazine.getContent();
        this.views = magazine.getViews();
        this.createdAt = magazine.getCreatedAt();
        this.updatedAt = magazine.getUpdatedAt();
        this.imageUrls = imageUrls;
    }
}
