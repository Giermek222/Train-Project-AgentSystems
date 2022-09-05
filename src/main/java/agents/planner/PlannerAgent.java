package agents.planner;

import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

public class PlannerAgent extends Agent {

    private List<String> segments = new ArrayList<>();

    @Override
    protected void setup() {
        super.setup();
        final Object[] params = getArguments();
        if (params.length < 2) {
            System.out.println("Usage [intersection name ], [segments separated by comas]");
            doDelete();
        }
        for (int i = 1; i < params.length; ++i) {
            segments.add(params[i].toString());
        }
    }
}
