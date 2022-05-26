package agents.intersection;

import jade.core.Agent;
import model.RailwayIntersection;
import simulation.Simulation;

import java.util.ArrayList;
import java.util.List;

public class IntersectionAgent extends Agent {

    private RailwayIntersection intersection;
    private List<String>  outgoing = new ArrayList<>();

    @Override
    protected void setup() {
        super.setup();
        final Object[] params = getArguments();
        if (params.length < 2) {
            System.out.println("Usage [train name ], [intersections separated by comas]");
            doDelete();
        }
        String intersectionName = params[0].toString();
        intersection = (RailwayIntersection) Simulation.getScene().getObject(intersectionName);
        for (int i = 1; i < params.length; ++i) {

            outgoing.add(params[i].toString());
        }
    }
}
