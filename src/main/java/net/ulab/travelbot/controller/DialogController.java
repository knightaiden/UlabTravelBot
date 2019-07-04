package net.ulab.travelbot.controller;

import net.ulab.travelbot.model.Message;
import net.ulab.travelbot.service.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by zhangzhe on 3/7/19.
 */
@RequestMapping("/")
@RestController
public class DialogController {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Autowired
    private DialogService dialogService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/sendMsg")
    public Message sendMsg(@RequestBody Message question) {
        Message answer = new Message();
        return answer;
    }

    @RequestMapping("/sendMsgSimple")
    public Message sendMsg() throws IOException {
        String q = "";
        String ans = dialogService.sendMsg(q);
        Message answer = new Message();
        answer.setContent(ans);
        return answer;
    }

}
