package custom.capstone.domain.notice.dto.response;

import custom.capstone.domain.notice.domain.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class NoticeListResponseDto {
    private Long noticeId;
    private String title;
    private int views;
    private LocalDateTime createdAt;
    private String thumbnailUrl;

    public NoticeListResponseDto(final Notice notice, final String thumbnailUrl) {
        this.noticeId = notice.getId();
        this.title = notice.getTitle();
        this.views = notice.getViews();
        this.createdAt = notice.getCreatedAt();
        this.thumbnailUrl = thumbnailUrl;
    }
}
