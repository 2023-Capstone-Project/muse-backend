package custom.capstone.domain.members.api;

import custom.capstone.domain.members.application.MemberService;
import custom.capstone.domain.members.dto.SignInMemberDto;
import custom.capstone.domain.members.dto.SignUpMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입
     */
    @PostMapping("/auth/signup")
    public ResponseEntity<String> signUp(@Valid SignUpMemberDto request) {
        memberService.join(request);
        return ResponseEntity.ok().body("가입이 완료되었습니다.");
    }

    /**
     * 로그인
     */
    @PostMapping("/auth/signin")
    public ResponseEntity<String> signIn(@Valid SignInMemberDto request) {
        memberService.login(request);
        return ResponseEntity.ok().body("로그인에 성공했습니다.");
    }

}
