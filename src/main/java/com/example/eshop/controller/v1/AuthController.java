package com.example.eshop.controller.v1;

import com.example.eshop.auth.service.AuthService;
import com.example.eshop.controller.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    /**
     * 아이디 중복조회
     *
     * @author hjkim
     * @param id
     */
    @GetMapping(value="/check/duplicated-id")
    public boolean checkDuplicatedId(@RequestParam String id) {
        logger.info("checkDuplicatedId ::: {}", id);

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
        logger.info("signin ::: {}", userDto);

        authService.signin(userDto);
    }
}