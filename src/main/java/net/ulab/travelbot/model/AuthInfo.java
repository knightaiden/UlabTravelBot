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
public class AuthInfo {

    private long id;
    private String partner;
    private String client_key;
    private String secret_key;
    private String token;
    private String last_update;

}
