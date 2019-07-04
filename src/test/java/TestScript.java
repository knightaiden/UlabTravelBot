import com.google.gson.Gson;
import net.ulab.travelbot.model.PatConvResponse;

/**
 * Created by zhangzhe on 4/7/19.
 */
public class TestScript {

    public static void main(String args[]){
        String input = "{\"success\":true,\"message\":\"Successfully conversed with pat.\",\"data\":{\"user_key\":\"452284e8-d1b8-4a08-8783-7374801d0abe\",\"data_to_match\":\"John is eating\",\"converse_response\":{\"CurrentResponse\":{\"Speaker\":\"me:\",\"WhatSaid\":\"Got it\",\"ConverseResultData\":{\"MatchNumber\":0,\"FoundNumber\":0,\"NoPolarFound\":false,\"PolarNoFound\":false,\"PolarYesFound\":false,\"NoContentFound\":false,\"ContentFound\":false,\"NothingFound\":false}}}}}";
        Gson gson = new Gson();
        PatConvResponse res = gson.fromJson(input, PatConvResponse.class);
        System.out.println(res);
    }

}
