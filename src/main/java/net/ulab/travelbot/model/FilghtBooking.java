package net.ulab.travelbot.model;

import lombok.Data;

/**
 * Created by zhangzhe on 16/7/19.
 */
@Data
public class FilghtBooking {

    private long id;
    private String user_id;
    private String departure_date;
    private int num_people;
    private int price_range;

}
