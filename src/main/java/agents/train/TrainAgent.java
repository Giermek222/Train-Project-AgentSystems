package agents.train;

import agents.train.behaviours.*;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import model.RailwayTrain;
import planner.CentralizedPlanner;
import simulation.Simulation;

import static agents.AgentConstants.TRAIN_DESCRIPTION;

public class TrainAgent extends Agent {
    private String finalDestination;

    @Override
    protected void setup() {
        super.setup();
        final Object[] params = getArguments();
        if (params.length < 4) {
            System.out.println("Usage [train name], [priority], [starting station], [pair <segment name, intersection name>]");
            doDelete();
        }
        String trainName = params[0].toString();
        RailwayTrain train = (RailwayTrain) Simulation.getScene().getObject(trainName);
        String priorityName = params[1].toString();

        CentralizedPlanner.RoutePriority priority;
        switch (priorityName) {
            case "DISTANCE" -> priority = CentralizedPlanner.RoutePriority.DISTANCE;
            case "COST" -> priority = CentralizedPlanner.RoutePriority.COST;
            case "LOAD" -> priority = CentralizedPlanner.RoutePriority.LOAD;
            default -> priority = CentralizedPlanner.RoutePriority.DEFAULT;
        }

        for (int i = 2; i < params.length; ++i) {
            if (i % 2 == 0) {
                train.intersections.add(params[i].toString());
                finalDestination = params[i].toString();
            }
            else
                train.segments.add(params[i].toString());
        }

        final DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());

        for (String segment : train.segments) {
            final ServiceDescription serviceDescription = new ServiceDescription();
            serviceDescription.setType(TRAIN_DESCRIPTION);
            serviceDescription.setName(segment);
            description.addServices(serviceDescription);
        }
        try {
            DFService.register(this, description);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }


        addBehaviour(AnnounceArrivalToIntersection.create(train));
        addBehaviour(AdjustSpeed.create(train));
        addBehaviour(StartRide.create(train));
        addBehaviour(AcknowledgeReroute.create(train, finalDestination, priority));
        addBehaviour(ApplyNewRoute.create(train, description));





    }
}
