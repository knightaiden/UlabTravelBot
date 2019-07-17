package net.ulab.travelbot.model;

import lombok.Data;
import net.ulab.travelbot.model.patresponse.PatConvData;

/**
 * Created by zhangzhe on 4/7/19.
 */
@Data
public class PatConvResponse {

    private boolean success;
    private String message;
    private PatConvData data;


}
