package agents.train.behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import model.RailwayTrain;
import model.messageparams.TrainRerouteParams;
import planner.CentralizedPlanner;

import java.io.IOException;
import java.util.Objects;

import static jade.lang.acl.ACLMessage.*;

public class AcknowledgeReroute extends CyclicBehaviour {
    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(PROPOSE);
    private final RailwayTrain train;
    private String destination;
    private CentralizedPlanner.RoutePriority priority;


    public AcknowledgeReroute(RailwayTrain train, String destination, CentralizedPlanner.RoutePriority priority) {
        this.train = train;
        this.destination = destination;
        this.priority = priority;
    }

    public static AcknowledgeReroute create(RailwayTrain train, String destination, CentralizedPlanner.RoutePriority priority) {
        return new AcknowledgeReroute(train,  destination, priority);
    }


    @Override
    public void action() {

        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message))
        {

            ACLMessage disqualifyRoute = new ACLMessage(INFORM_IF);
            disqualifyRoute.addReceiver(new AID("planner", AID.ISLOCALNAME));
            disqualifyRoute.setContent(train.getName());
            myAgent.send(disqualifyRoute);

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
