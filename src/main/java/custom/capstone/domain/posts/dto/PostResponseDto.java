package custom.capstone.domain.posts.dto;

import custom.capstone.domain.members.domain.Member;

public record PostResponseDto(
        Long id,
        String title,
        String content,
        Member author
) {
}
