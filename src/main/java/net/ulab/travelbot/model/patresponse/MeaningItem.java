package net.ulab.travelbot.model.patresponse;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangzhe on 15/7/19.
 */
@Data
public class MeaningItem {

    private List<UndergoerData> Undergoer;
    private String IllocForce;
    private String QuestionType;
    private String Predicate;
    private List<String> Actor;

}
