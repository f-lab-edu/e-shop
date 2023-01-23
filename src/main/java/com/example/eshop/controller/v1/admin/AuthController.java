package com.example.eshop.controller.v1.admin;

import com.example.eshop.member.auth.service.AdminAuthService;
import com.example.eshop.controller.dto.AdminUserDto;
import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import com.example.eshop.member.core.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController("AdminAuthController")
@RequestMapping(value="/v1/admin/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AdminAuthService adminAuthService;
    private final AdminMemberService adminMemberService;

    /**
     * 아이디 중복조회
     *
     * @author hjkim
     * @param id
     */
    @GetMapping(value="/check/duplicated-id")
    public boolean checkDuplicatedId(@RequestParam String id) {
        log.info("checkDuplicatedId ::: {}", id);

        return adminMemberService.isDuplicatedId(id);
    }

    /**
     * 회원가입
     *
     * @author hjkim
     * @param adminUserDto
     */
    @PostMapping(value="/signin")
    public void signin(@Valid @RequestBody AdminUserDto adminUserDto) {
        log.info("signin ::: {}", adminUserDto);

        adminMemberService.signin(adminUserDto);
    }

    /**
     * 로그인
     *
     * @author hjkim
     * @param loginDto
     */
    @PostMapping(value="/login")
    public TokenDto login(@Valid @RequestBody LoginDto loginDto) {
        log.info("login ::: {}", loginDto);

        return adminAuthService.login(loginDto);
    }

    /**
     * 로그아웃
     *
     * @author hjkim
     */
    @PostMapping(value="/logout")
    public void logout() {
        log.info("logout");
    }

    /**
     * 토큰 갱신
     *
     * @author hjkim
     */
    @GetMapping(value="/token/refresh")
    public TokenDto refreshToken(@RequestAttribute long userSeq) {
        log.info("refreshToken ::: {}", userSeq);

        return adminAuthService.refreshToken(userSeq);
    }
}