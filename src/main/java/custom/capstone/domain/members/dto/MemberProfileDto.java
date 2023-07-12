package custom.capstone.domain.members.dto;

public record MemberProfileDto (
        Long id,
        String nickname
//        String profileImage  -> TODO: 이미지 구현 후 적용하기
) {
}
