package custom.capstone.global.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum BaseResponseStatus {
    /**
     * 1000: 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000: Request 오류
     */
    // MEMBER
    EMPTY_JWT(false, 2000, "JWT 가 존재하지 않습니다."),
    INVALID_JWT(false, 2001, "유효하지 않은 JWT 입니다."),
    POST_MEMBER_EXISTS_EMAIL(false, 2002, "이미 존재하는 이메일입니다."),
    POST_MEMBER_EXISTS_NICKNAME(false, 2003, "이미 존재하는 닉네임입니다."),
    WRONG_PASSWORD(false, 2004, "잘못된 비밀번호입니다."),
    WRONG_JOIN_PASSWORD(false, 2005, "비밀번호 확인이 일치하지 않습니다."),
    FOLLOW_EXISTS_TO(false, 2006, "이미 팔로우 했습니다."),
    MEMBER_NOT_FOUND(false, 2007, "해당 사용자를 찾을 수 없습니다."),

    // POST
    POST_NOT_FOUND(false, 2100, "해당 게시글을 찾을 수 없습니다."),

    // MAGAZINE
    MAGAZINE_NOT_FOUND(false, 2200, "해당 매거진을 찾을 수 없습니다."),

    // NOTICE
    NOTICE_NOT_FOUND(false, 2300, "해당 공지사항을 찾을 수 없습니다."),

    // INQUIRY & ANSWER
    INQUIRY_NOT_FOUND(false, 2400, "해당 1:1 문의를 찾을 수 없습니다."),
    INQUIRY_CANNOT_DELETE(false, 2401, "문의 작성자만 삭제할 수 있습니다."),
    ANSWER_NOT_FOUND(false, 2402, "해당 1:1 문의에 대한 답변을 찾을 수 없습니다."),

    // INTEREST
    INTEREST_NOT_FOUND(false, 2500, "해당 좋아요 게시글을 찾을 수 없습니다."),

    // REVIEW
    REVIEW_NOT_FOUND(false, 2600, "해당 후기를 찾을 수 없습니다."),

    // TRADING
    TRADING_NOT_FOUND(false, 2700, "해당 거래를 찾을 수 없습니다."),

    // JWT


    /**
     * 3000: Response 오류
     */
    // COMMON
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    PASSWORD_ENCRYPTION_ERROR(false, 3001, "비밀번호 암호화에 실패하였습니다."),

    /**
     * 4000: DB, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버 연결에 실패하였습니다."),

    // MEMBER
    POST_MEMBER_FAILED_JOIN(false, 2009, "회원가입에 실패하였습니다."),
    POST_MEMBER_FAILED_LOGIN(false, 2010, "로그인에 실패하였습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(final boolean isSuccess, final int code, final String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
