package custom.capstone.domain.magazine.dto.response;

import custom.capstone.domain.magazine.domain.Magazine;
import custom.capstone.domain.members.dto.MemberProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class MagazineListResponseDto {
    private Long magazineId;
    private String title;
    private MemberProfileDto admin;
    private int views;
    private String thumbnailUrl;

    public MagazineListResponseDto(final Magazine magazine, final String thumbnailUrl) {
        this.magazineId = magazine.getId();
        this.title = magazine.getTitle();
        this.admin = new MemberProfileDto(
                magazine.getMember().getId(),
                magazine.getMember().getNickname(),
                magazine.getMember().getProfileImage()
        );
        this.views = magazine.getViews();
        this.thumbnailUrl = thumbnailUrl;
    }
}