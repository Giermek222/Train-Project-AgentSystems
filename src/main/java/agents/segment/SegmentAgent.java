package agents.segment;

import jade.core.Agent;


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
        firstIntersection = params[1].toString();
        secondIntersection = params[2].toString();
    }
}
