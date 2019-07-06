package net.ulab.travelbot.service;

import com.google.gson.Gson;
import net.ulab.travelbot.mapper.PatUsers;
import net.ulab.travelbot.model.Message;
import net.ulab.travelbot.model.PatConvRequest;
import net.ulab.travelbot.model.PatConvResponse;
import net.ulab.travelbot.model.PatUser;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by zhangzhe on 3/7/19.
 */
@Service
public class DialogService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Value("${pat.converse.url}")
    private String patConvUrl;

    @Value("${pat.auth.id}")
    private long patId;

    @Autowired
    private AuthService authService;

    @Autowired
    private PatUsers patUsers;

    @Autowired
    private OkHttpClient okHttpClient;

    public Message processMsg(Message message) {
        String token = authService.getTokenById(patId);
        Gson phaser = new Gson();
        PatConvRequest requestData = new PatConvRequest();
        String userKey = processUserKey(message.getSpeakerId());
        requestData.setUser_key(userKey);
        requestData.setData_to_match(message.getContent());
        Request request = new Request.Builder()
                .url(patConvUrl)
                .addHeader("Authorization", token)
                .post(RequestBody
                        .create(MediaType.parse("application/json"),
                                phaser.toJson(requestData)))
                .build();
        Message answer = new Message();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String res = response.body().string();
            logger.info(res);
            PatConvResponse patResponse = phaser.fromJson(res, PatConvResponse.class);
            if ("".equals(userKey)) {
                PatUser newUser = new PatUser();
                newUser.setPat_user_key(patResponse.getData().getUser_key());
                newUser.setFrontend_key(message.getSpeakerId());
                patUsers.insertPatUserInfo(newUser);
            }
            answer.setSpeakerId(patResponse.getData().getUser_key());
            answer.setContent(patResponse.getData().getConverse_response().getCurrentResponse().getWhatSaid());
        } catch (IOException e) {
            logger.error("Problem on connecting PAT api service!");
            answer.setContent("hmmmm.... not quit understand");
        }
        return answer;
    }

    private String processUserKey(String speakerId) {
        PatUser user = patUsers.getPatUserByFID(speakerId);
        if (user == null) {
            return "";
        }
        return user.getPat_user_key();
    }

//    public String sendMsg(String question) throws IOException {
//        String token = authService.getTokenById(patId);
//        Request request = new Request.Builder()
//                .url(patConvUrl)
//                .addHeader("Authorization", token)
//                .post(RequestBody
//                        .create(MediaType.parse("application/json"),
//                                "{\n" +
//                                        "  \"data_to_match\": \"what is shark doing?\",\n" +
//                                        "  \"user_key\": \"eedf54ae-fe22-4a50-bff2-061ec1618d54\"\n" +
//                                        "}"))
//                .build();
//        Response response = okHttpClient.newCall(request).execute();
//        String res = response.body().string();
//        logger.info("chat result : " + res);
//        return res;
//    }

}
