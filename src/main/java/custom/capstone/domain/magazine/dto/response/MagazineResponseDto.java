package custom.capstone.domain.magazine.dto.response;

import custom.capstone.domain.magazine.domain.Magazine;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MagazineResponseDto {
    private String title;
    private String content;
    private int views;
    // TODO: image Urls
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MagazineResponseDto(final Magazine magazine) {
        this.title = magazine.getTitle();
        this.content = magazine.getContent();
        this.views = magazine.getViews();
        this.createdAt = magazine.getCreatedAt();
        this.updatedAt = magazine.getUpdatedAt();
    }
}
