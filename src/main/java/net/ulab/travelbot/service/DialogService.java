package net.ulab.travelbot.service;

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

    public String sendMsg(String question) throws IOException {
        String token = authService.getTokenById(patId);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(patConvUrl)
                .addHeader("Authorization", token)
                .post(RequestBody
                        .create(MediaType.parse("application/json"),
                                "{\n" +
                                        "  \"data_to_match\": \"what is shark doing?\",\n" +
                                        "  \"user_key\": \"eedf54ae-fe22-4a50-bff2-061ec1618d54\"\n" +
                                        "}"))
                .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string();
        logger.info("chat result : " + res);
        return res;
    }

}
