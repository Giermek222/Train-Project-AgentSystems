package agents.segment;

import agents.segment.behaviours.CheckHealth;
import jade.core.Agent;
import model.RailwayIntersection;
import model.RailwaySegment;
import simulation.Simulation;


public class SegmentAgent extends Agent {
    private String firstIntersection;
    private String secondIntersection;
    @Override
    protected void setup() {
        super.setup();

        final Object[] params = getArguments();
        if (params.length != 3) {
            System.out.println("Usage [segment name ], [first intersection], [second intersection]");
            doDelete();
        }
        String name = params[0].toString();

        firstIntersection = params[1].toString();
        secondIntersection = params[2].toString();
        RailwaySegment segment = (RailwaySegment) Simulation.getScene().getObject("segment_" + name);
        addBehaviour(CheckHealth.create(this, 1000, segment));
    }
}
