package agents.train.behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import model.RailwayIntersection;
import model.RailwayTrain;
import model.messageparams.TrainToIntersectionInfo;
import simulation.Simulation;

import java.io.IOException;
import java.util.Objects;

import static agents.AgentConstants.FINAL_STATION;
import static jade.lang.acl.ACLMessage.CONFIRM;
import static jade.lang.acl.ACLMessage.INFORM_IF;

public class AnnounceArrivalToIntersection extends CyclicBehaviour {
    private final RailwayTrain train;
    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(CONFIRM);

    public AnnounceArrivalToIntersection(RailwayTrain train) {
        this.train = train;
    }

    public static AnnounceArrivalToIntersection create(RailwayTrain train) {
        return new AnnounceArrivalToIntersection(train);
     }

    @Override
    public void action() {
        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message) && train.isRoadStable()) {

            if (message.getContent().equals(FINAL_STATION))
            {
                train.setSpeed(0);
                train.setColor(255,0,0);
                ACLMessage disqualifyRoute = new ACLMessage(INFORM_IF);
                disqualifyRoute.addReceiver(new AID("planner", AID.ISLOCALNAME));
                disqualifyRoute.setContent(train.getName());
                myAgent.send(disqualifyRoute);
                myAgent.doDelete();
            }
            else
            {
                System.out.println("sending message to next intersection:" + train.intersections.peek());
                final ACLMessage proposal = new ACLMessage(ACLMessage.INFORM);
                TrainToIntersectionInfo messageContent = new TrainToIntersectionInfo();
                messageContent.setMaxSpeed(train.getMaxSpeed());
                messageContent.setPreviousIntersection(train.getPreviousIntersection().getName());

                RailwayIntersection last = (RailwayIntersection) Simulation.getScene().getObject(train.intersections.peek());
                train.setPreviousIntersection(last);
                proposal.addReceiver(new AID(train.intersections.remove(), AID.ISLOCALNAME));

                try {
                    proposal.setContentObject(messageContent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                myAgent.send(proposal);
            }
        }
    }
}
