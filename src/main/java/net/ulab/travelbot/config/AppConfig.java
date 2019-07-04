package net.ulab.travelbot.config;

import okhttp3.OkHttpClient;
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

}
