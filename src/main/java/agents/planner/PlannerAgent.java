package agents.planner;

import agents.planner.behaviours.HandleMalfunction;
import jade.core.Agent;
import model.RailwayPlan;

import java.util.ArrayList;
import java.util.List;

public class PlannerAgent extends Agent {

    private List<String> segments = new ArrayList<>();

    @Override
    protected void setup() {
        super.setup();
        final Object[] params = getArguments();
        if (params.length < 2) {
            System.out.println("Usage [planner name ], [segments separated by comas]");
            doDelete();
        }
        for (int i = 1; i < params.length; ++i) {
            segments.add(params[i].toString());
        }

        RailwayPlan plan = new RailwayPlan(segments);
        addBehaviour(HandleMalfunction.create());

    }
}
