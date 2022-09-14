package model.messageparams;

import java.io.Serializable;
import java.util.List;

public class TrainPlannerRegisterParams implements Serializable {
    private final List<String> route;
    private final String name;
    private final Float maxSpeed;

    public TrainPlannerRegisterParams(List<String> route, String name, Float maxSpeed) {
        this.route = route;
        this.name = name;
        this.maxSpeed = maxSpeed;
    }

    public List<String> getRoute() {
        return route;
    }

    public String getName() {
        return name;
    }

    public Float getMaxSpeed() {
        return maxSpeed;
    }
}
