package custom.capstone.domain.magazine.dto.response;

import custom.capstone.domain.magazine.domain.Magazine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class MagazineListResponseDto {
    private String title;
    private String content;
    private int views;

    public MagazineListResponseDto(final Magazine magazine) {
        this.title = magazine.getTitle();
        this.content = magazine.getContent();
        this.views = magazine.getViews();
    }
}