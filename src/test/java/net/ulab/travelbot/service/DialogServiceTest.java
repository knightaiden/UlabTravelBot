package net.ulab.travelbot.service;

import net.ulab.travelbot.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by zhangzhe on 15/7/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
@TestPropertySource("classpath:application-dev.properties")
public class DialogServiceTest {

    @Autowired
    private DialogService dialogService;

    @Autowired
    private AuthService authService;

    @Test
    public void processMeaning() throws Exception {
        //authService.updatePatToken();
        String authToken = authService.getTokenById(1);
        dialogService.processMeaning("Book a flight", authToken);



    }

}