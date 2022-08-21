package model.messageparams;

import java.io.Serializable;

public class IntersectionResponse implements Serializable {
    public final Float speed;
    public final long time;

    public IntersectionResponse(Float speed, long time) {
        this.speed = speed;
        this.time = time;
    }
}
