package agents.segment;

import agents.segment.behaviours.CheckHealth;
import jade.core.Agent;
import model.RailwaySegment;
import simulation.Simulation;


public class SegmentAgent extends Agent {

    @Override
    protected void setup() {
        super.setup();

        final Object[] params = getArguments();
        if (params.length != 3) {
            System.out.println("Usage [segment name ]");
            doDelete();
        }
        String name = params[0].toString();
        RailwaySegment segment = (RailwaySegment) Simulation.getScene().getObject("segment_" + name);
        addBehaviour(CheckHealth.create(this, 1000, segment));
    }
}
