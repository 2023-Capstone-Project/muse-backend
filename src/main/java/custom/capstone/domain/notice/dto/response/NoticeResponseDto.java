package custom.capstone.domain.notice.dto.response;

import custom.capstone.domain.notice.domain.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class NoticeResponseDto {
    private String title;
    private String content;
    private int views;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NoticeResponseDto(final Notice notice) {
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.views = notice.getViews();
        this.createdAt = notice.getCreatedAt();
        this.updatedAt = notice.getUpdatedAt();
    }
}
