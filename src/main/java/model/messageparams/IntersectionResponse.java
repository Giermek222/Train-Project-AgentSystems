package model.messageparams;

import java.io.Serializable;

public class IntersectionResponse implements Serializable {
    public final Float speed;
    public final Float time;

    public IntersectionResponse(Float speed, Float time) {
        this.speed = speed;
        this.time = time;
    }
}
