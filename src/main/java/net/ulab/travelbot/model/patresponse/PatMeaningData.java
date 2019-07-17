package net.ulab.travelbot.model.patresponse;

import lombok.Data;
import net.ulab.travelbot.model.PatMeaningRequest;
import net.ulab.travelbot.model.PatMeaningResponse;

import java.util.List;

/**
 * Created by zhangzhe on 15/7/19.
 */
@Data
public class PatMeaningData {

    private PatMeaningRequest request;
    private List<MeaningItem> response;
}
