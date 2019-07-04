package net.ulab.travelbot.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zhangzhe on 3/7/19.
 */
@SuppressWarnings("unused")
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class Message {

    private long topicId;
    private int type;
    private String content;

}
