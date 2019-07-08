package net.ulab.travelbot.service;

import com.google.gson.Gson;
import net.ulab.travelbot.mapper.AuthMapper;
import net.ulab.travelbot.model.AuthInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzhe on 3/7/19.
 */
@Service
public class AuthService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Value("${pat.auth.id}")
    private long patAuthId;

    @Value("${pat.auth.url}")
    private String patAuthUrl;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private OkHttpClient okHttpClient;

    public AuthInfo getAuthInfoById(long id) {
        return authMapper.selectById(id);
    }

    public String getTokenById(long id) {
        AuthInfo authInfo = authMapper.selectById(id);
        return authInfo.getToken();
    }

    public boolean updateToken(long id, String token) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String current = sdf.format(new Date());
        return authMapper.updateTokenById(token, id, current) == 1 ? true : false;
    }


    public void updatePatToken() throws IOException {
        logger.info("starting to update pat token");
        AuthInfo patAuthInfo = getAuthInfoById(patAuthId);
        String refreshTokenUrl = String.format(patAuthUrl, patAuthInfo.getClient_key(), patAuthInfo.getSecret_key());
        Request request = new Request.Builder()
                .url(refreshTokenUrl)
                .build();
        logger.info("sending request to pat server");
        logger.debug(refreshTokenUrl);
        Response response = okHttpClient.newCall(request).execute();
        String result = response.body().string();
        logger.debug(refreshTokenUrl);
        Gson gson = new Gson();
        Map formattedResult = gson.fromJson(result, HashMap.class);
        boolean isSuccess = (boolean) formattedResult.get("success");
        if (isSuccess) {
            logger.info("received success response from pat");
            String token = (String) ((Map) formattedResult.get("data")).get("auth_token");
            System.out.println("result: "+token);
            boolean update_result = this.updateToken(patAuthId, token);
            if (update_result) {
                logger.info("update pat token successfully");
            } else {
                logger.error("Unable to update the token: db error");
            }
        } else {
            logger.error("Unable to update the token:" + formattedResult.get("errors"));
        }
    }

}
