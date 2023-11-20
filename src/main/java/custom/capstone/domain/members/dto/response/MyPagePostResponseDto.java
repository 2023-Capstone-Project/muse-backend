package custom.capstone.domain.members.dto.response;

import custom.capstone.domain.members.dto.MemberProfileDto;
import custom.capstone.domain.posts.dto.response.PostListResponseDto;

import java.util.List;

public record MyPagePostResponseDto(
        MemberProfileDto memberProfileDto,
        List<PostListResponseDto> postListResponseDto
) {
}
