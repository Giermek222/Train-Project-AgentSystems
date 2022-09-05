package model.messageparams;

import java.io.Serializable;

public class TrainToIntersectionInfo implements Serializable {
    private String previousIntersection;
    private Float maxSpeed;


    public String getPreviousIntersection() {
        return previousIntersection;
    }

    public void setPreviousIntersection(String previousIntersection) {
        this.previousIntersection = previousIntersection;
    }

    public Float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
