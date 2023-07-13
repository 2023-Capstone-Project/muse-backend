package custom.capstone.domain.notice.dto.response;

import custom.capstone.domain.notice.domain.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class NoticeListResponseDto {
    private String title;
    private String content;
    private int views;

    public NoticeListResponseDto(final Notice notice) {
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.views = notice.getViews();
    }
}
