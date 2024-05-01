package com.a702.hup.application.contorller;

import com.a702.hup.application.data.request.MemberSignUpRequest;
import com.a702.hup.application.data.response.IdCheckResponse;
import com.a702.hup.application.data.response.MemberInfoResponse;
import com.a702.hup.domain.member.MemberService;
import com.a702.hup.global.config.security.SecurityUserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    /**
     * @author 이경태
     * @date 2024-04-29
     * @description 회원 가입
     **/
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody MemberSignUpRequest memberSignUpRequest) {
        log.info("[+] MemberController :: signUp :: start");
        memberService.signUp(memberSignUpRequest
                .toEntity(passwordEncoder.encode(memberSignUpRequest.getPassword())));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * @author 이경태
     * @date 2024-04-29
     * @description 아이디 중복 검사
     **/
    @GetMapping("/check")
    public ResponseEntity<IdCheckResponse> idCheck(@RequestParam String userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.idCheck(userId));
    }

    /**
     * @author 이경태
     * @date 2024-04-29
     * @description 회원 정보 조회
     **/
    @GetMapping
    public ResponseEntity<MemberInfoResponse> getInfo(
            @RequestParam int memberId,
            @AuthenticationPrincipal SecurityUserDetailsDto securityUserDetailsDto
    ) {
        log.info("[+] MemberController :: getInfo :: securityUserDetailsDto: {}", securityUserDetailsDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.findMemberInfoById(memberId, securityUserDetailsDto));
    }

//    public ResponseEntity<Void> updateInfo(@RequestParam int memberId, MemberInfoResponse memberInfoResponse) {}
}
