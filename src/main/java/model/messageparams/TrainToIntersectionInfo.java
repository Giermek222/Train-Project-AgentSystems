package model.messageparams;

import java.io.Serializable;

public class TrainToIntersectionInfo implements Serializable {
    private String secondIntersection;
    private Float maxSpeed;


    public String getSecondIntersection() {
        return secondIntersection;
    }

    public void setSecondIntersection(String secondIntersection) {
        this.secondIntersection = secondIntersection;
    }

    public Float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
