package net.ulab.travelbot.service;

import com.google.gson.Gson;
import net.ulab.travelbot.mapper.PatUsers;
import net.ulab.travelbot.model.*;
import net.ulab.travelbot.model.patresponse.MeaningItem;
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

    @Value("${pat.meaning.url}")
    private String patMeaningUrl;

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

    public Message sendMeaning(Message message) {
        String token = authService.getTokenById(patId);
        Message responseToClient = new Message();
        String result = processMeaning(message.getContent(), token);
        if (!"".equals(result)) {
            responseToClient.setSpeakerId(message.getSpeakerId());
            responseToClient.setContent(result);
        } else {
            responseToClient = processMsg(message);
        }
        return responseToClient;
    }

    public String processMeaning(String message, String authToken){
        Gson phaser = new Gson();
        PatMeaningRequest patRequest = new PatMeaningRequest();
        if ("sydney".equals(message.toLowerCase()) || "perth".equals(message.toLowerCase())
                || "beijing".equals(message.toLowerCase())) {
            return "You are currently in Melbourne, shall I find a Melbourne to "+message+" flight.";
        }
        patRequest.setText(message);
        Request request = new Request.Builder()
                .url(patMeaningUrl)
                .addHeader("Authorization", authToken)
                .post(RequestBody
                        .create(MediaType.parse("application/json"),
                                phaser.toJson(patRequest)))
                .build();
        PatMeaningResponse patResponse = new PatMeaningResponse();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String res = response.body().string();
            logger.info(res);
            patResponse = phaser.fromJson(res, PatMeaningResponse.class);
        } catch (IOException e) {
            logger.error("Problem on connecting PAT api service!");
            return "";
        }
        if (patResponse.getData() == null) {
            return "";
        }
        if (isContainedUndergoer(patResponse)) {
            for (MeaningItem item : patResponse.getData().getResponse()) {
                if (item.getUndergoer().get(0).getMatches().contains("flight")) {
                    return "NP. Where do you want to go";
                }
            }
        }

        if (isContainedUndergoer(patResponse) && isContainedActor(patResponse)) {
            for (MeaningItem item : patResponse.getData().getResponse()) {
                if (item.getUndergoer().get(0).getMatches().toLowerCase().equals("what")
                        && item.getActor().get(0).toLowerCase().contains("price")) {
                    return "They range from $200 - $500";
                }
            }
        }
        return "";
    }

    private boolean isContainedUndergoer(PatMeaningResponse patResponse) {
        if (patResponse.getData().getResponse().size()>0) {
            for (MeaningItem item : patResponse.getData().getResponse()) {
                if (item.getUndergoer().size() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isContainedActor(PatMeaningResponse patResponse) {
        if (patResponse.getData().getResponse().size()>0) {
            for (MeaningItem item : patResponse.getData().getResponse()) {
                if (item.getActor().size() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

}
