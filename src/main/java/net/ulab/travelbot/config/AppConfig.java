package net.ulab.travelbot.config;

import com.github.messenger4j.Messenger;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangzhe on 4/7/19.
 */
@Configuration
public class AppConfig {

    @Bean
    public OkHttpClient okHttpClient(){
        OkHttpClient client = new OkHttpClient();
        return client;
    }

    @Bean
    public Messenger messenger(@Value("${messenger4j.pageAccessToken}") String pageAccessToken,
                               @Value("${messenger4j.appSecret}") final String appSecret,
                               @Value("${messenger4j.verifyToken}") final String verifyToken) {
        return Messenger.create(pageAccessToken, appSecret, verifyToken);
    }

}
