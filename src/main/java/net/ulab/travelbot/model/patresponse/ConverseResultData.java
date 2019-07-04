package net.ulab.travelbot.model.patresponse;

/**
 * Created by zhangzhe on 4/7/19.
 */
@lombok.Data
public class ConverseResultData {
    private int MatchNumber;
    private int FoundNumber;
    private boolean NoPolarFound;
    private boolean PolarNoFound;
    private boolean PolarYesFound;
    private boolean NoContentFound;
    private boolean ContentFound;
    private boolean NothingFound;
}
