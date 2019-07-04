package net.ulab.travelbot.controller;

import net.ulab.travelbot.model.AuthInfo;
import net.ulab.travelbot.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by zhangzhe on 3/7/19.
 */
@RequestMapping("/auth")
@RestController
public class AuthController {

    @Value("${pat.auth.id}")
    private long patAuthId;

    @Autowired
    private AuthService authService;

    @RequestMapping("/refresh")
    public String refreshAuth() throws IOException {
        authService.updatePatToken();
        return "success";
    }
}
