package model.messageparams;

import java.io.Serializable;

public class TrainRerouteParams implements Serializable {
    private final float speed;
    private final float maxSpeed;
    private final String beginning;
    private final String end;

    public TrainRerouteParams(float speed, float maxSpeed, String beginning, String end) {
        this.speed = speed;
        this.maxSpeed = maxSpeed;
        this.beginning = beginning;
        this.end = end;
    }

    public float getSpeed() {
        return speed;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public String getBeginning() {
        return beginning;
    }

    public String getEnd() {
        return end;
    }
}
