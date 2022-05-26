package agents.intersection;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import model.RailwayIntersection;
import model.RailwaySegment;
import simulation.Simulation;

import java.util.Iterator;

public class TestIntersectionAgent extends Agent {
    @Override
    protected void setup () {
        super.setup ();

        final Object [] params = getArguments ();
        String intersectionName = (String) params[0];

        RailwayIntersection controller = (RailwayIntersection) Simulation.getScene ().getObject (intersectionName);
        TickerBehaviour tickerBehaviour = new TickerBehaviour (this, 1000) {
            private Iterator<RailwaySegment> currentSegment = controller.outbound ();

            @Override
            protected void onTick () {
                if (!currentSegment.hasNext ()) {
                    currentSegment = controller.outbound ();
                }

                RailwaySegment segment = currentSegment.next ();
                System.out.printf ("%s setting next to %s\n", controller.getName (), segment.getName ());
                controller.setNextSegment (segment);
            }
        };

        addBehaviour (tickerBehaviour);
    }
}
