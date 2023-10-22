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
    private String title;
    private String content;
    private int views;
    private LocalDateTime createdAt;

    public NoticeListResponseDto(final Notice notice) {
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.views = notice.getViews();
        this.createdAt = notice.getCreatedAt();
    }
}
