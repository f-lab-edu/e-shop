package com.example.eshop.controller.v1;

import com.example.eshop.auth.service.AuthService;
import com.example.eshop.controller.dto.LoginDto;
import com.example.eshop.controller.dto.TokenDto;
import com.example.eshop.controller.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * 아이디 중복조회
     *
     * @author hjkim
     * @param id
     */
    @GetMapping(value="/check/duplicated-id")
    public boolean checkDuplicatedId(@RequestParam String id) {
        log.info("checkDuplicatedId ::: {}", id);

        return authService.isDuplicatedId(id);
    }

    /**
     * 회원가입
     *
     * @author hjkim
     * @param userDto
     */
    @PostMapping(value="/signin")
    public void signin(@Valid @RequestBody UserDto userDto) {
        log.info("signin ::: {}", userDto);

        authService.signin(userDto);
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

        return authService.login(loginDto);
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

        return authService.refreshToken(userSeq);
    }
}