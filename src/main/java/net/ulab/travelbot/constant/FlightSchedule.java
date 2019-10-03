package net.ulab.travelbot.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzhe on 17/7/19.
 */
public class FlightSchedule {

    static {
        flightSchedule = new HashMap<>();
        FlightSchedule.flightSchedule.put("Perth", "Sydney");
        FlightSchedule.flightSchedule.put("Melbourne", "Sydney");
        FlightSchedule.flightSchedule.put("Beijing", "Sydney");
        FlightSchedule.flightSchedule.put("Shanghai", "Sydney");
    }

    public static Map<String, String> flightSchedule;

}
