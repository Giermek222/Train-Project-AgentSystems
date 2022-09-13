package agents.train.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import model.RailwayTrain;
import model.messageparams.TrainRerouteParams;
import planner.CentralizedPlanner;

import java.io.IOException;
import java.util.Objects;
import java.util.Queue;

import static jade.lang.acl.ACLMessage.*;

public class AcknowledgeReroute extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(PROPOSE);

    private final RailwayTrain train;

    private Queue<String> segments;
    private Queue<String> intersections;

    private String destination;
    private CentralizedPlanner.RoutePriority priority;


    public AcknowledgeReroute(RailwayTrain train, Queue<String> segments, Queue<String> intersections, String destination, CentralizedPlanner.RoutePriority priority) {

        this.train = train;
        this.segments = segments;
        this.intersections = intersections;
        this.destination = destination;
        this.priority = priority;

    }

    public static AcknowledgeReroute create(RailwayTrain train, Queue<String> segments, Queue<String> intersections, String destination, CentralizedPlanner.RoutePriority priority) {
        return new AcknowledgeReroute(train, segments, intersections, destination, priority);
    }


    @Override
    public void action() {

        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message))
        {
            final String brokenIntersection = message.getContent();
            train.setSpeed(0);
            train.setColor(255,0,0);


                TrainRerouteParams responseParams = new TrainRerouteParams( train.getMaxSpeed(), train.getPreviousIntersection().getName(), destination, priority);
                ACLMessage response = new ACLMessage(CONFIRM);

                try {
                    response.setContentObject(responseParams);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                response.addReceiver(message.getSender());
                myAgent.send(response);
                train.setRoadStable(false);

        }
    }
}
