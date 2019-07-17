package net.ulab.travelbot.model;

import lombok.Data;
import net.ulab.travelbot.model.patresponse.PatMeaningData;

/**
 * Created by zhangzhe on 15/7/19.
 */
@Data
public class PatMeaningResponse {

    private boolean success;
    private String message;
    private PatMeaningData data;


}
