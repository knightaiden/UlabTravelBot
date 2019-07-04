package net.ulab.travelbot.model;

import lombok.Data;

/**
 * Created by zhangzhe on 4/7/19.
 */
@Data
public class PatConvResponse {

    private boolean success;
    private String message;
    private net.ulab.travelbot.model.patresponse.Data data;


}
