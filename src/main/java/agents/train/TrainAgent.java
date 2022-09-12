package agents.train;

import agents.train.behaviours.*;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import model.RailwayTrain;
import simulation.Simulation;

import java.util.*;

public class TrainAgent extends Agent {

    private RailwayTrain train;
    private final Queue<String> route_intersections = new ArrayDeque<>();
    private final Queue<String> route_segments = new ArrayDeque<>();

    private String finalDestination;

    @Override
    protected void setup() {
        super.setup();
        final Object[] params = getArguments();
        if (params.length < 4) {
            System.out.println("Usage [train name], [starting station], [pair <segment name, intersection name>]");
            doDelete();
        }
        String trainName = params[0].toString();
        train = (RailwayTrain) Simulation.getScene().getObject(trainName);
        for (int i = 1; i < params.length; ++i) {
            if (i % 2 != 0) {
                route_intersections.add(params[i].toString());
                finalDestination = params[i].toString();
            }
            else
                route_segments.add(params[i].toString());
        }

        final DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());

        for (String segment : route_segments) {
            final ServiceDescription serviceDescription = new ServiceDescription();
            serviceDescription.setType("Passing");
            serviceDescription.setName(segment);
            description.addServices(serviceDescription);
        }
        try {
            DFService.register(this, description);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }


        addBehaviour(AnnounceArrivalToIntersection.create(train, route_intersections, route_segments, train.getSpeed()));
        addBehaviour(AdjustSpeed.create(train, route_segments));
        addBehaviour(StartRide.create(train, route_intersections, route_segments, train.getSpeed()));
        addBehaviour(AcknowledgeReroute.create(train, route_segments, route_intersections, finalDestination));
        addBehaviour(ApplyNewRoute.create(train, route_intersections, route_segments));





    }
}
