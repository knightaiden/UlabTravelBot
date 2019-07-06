package net.ulab.travelbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhe on 6/7/19.
 */
@Component
public class AutoAuthRefresh implements ApplicationRunner {

    @Autowired
    private AuthService authService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        authService.updatePatToken();
    }

}
