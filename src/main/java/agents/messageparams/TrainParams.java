package agents.messageparams;

import java.io.Serializable;

public class TrainParams implements Serializable {
    public final String segment;
    public final float speed;

    public TrainParams(String segment, float speed) {

        this.segment = segment;
        this.speed = speed;
    }
}
