package agents.intersection;

import agents.intersection.behaviours.ChangeDirection;
import agents.intersection.behaviours.ReceiveArrivalInfo;
import jade.core.Agent;
import model.RailwayIntersection;
import simulation.Simulation;

import java.util.ArrayList;
import java.util.List;

public class InsersectionAgent extends Agent {

    private RailwayIntersection intersection;
    private List<String>  outgoing = new ArrayList<>();

    @Override
    protected void setup() {
        super.setup();
        final Object[] params = getArguments();
        if (params.length < 2) {
            System.out.println("Usage [intersection name ], [intersections separated by comas]");
            doDelete();
        }
        String intersectionName = params[0].toString();
        intersection = (RailwayIntersection) Simulation.getScene().getObject(intersectionName);
        for (int i = 1; i < params.length; ++i) {

            outgoing.add(params[i].toString());
        }

        addBehaviour(ReceiveArrivalInfo.create(intersection));
        addBehaviour(ChangeDirection.create(intersection));
    }
}
