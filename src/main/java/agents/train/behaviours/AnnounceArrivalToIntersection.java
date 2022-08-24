package agents.train.behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import model.RailwayTrain;

import java.util.Objects;
import java.util.Queue;

import static jade.lang.acl.ACLMessage.CONFIRM;

public class AnnounceArrivalToIntersection extends CyclicBehaviour {
    private final RailwayTrain train;

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(CONFIRM);
   private final Queue<String> intersections;
   private final Queue<String> segmentrs;
   private final Float currentSpeed;

    public AnnounceArrivalToIntersection(RailwayTrain train, Queue<String> intersectionName, Queue<String> segmentName, float speed) {
        this.train = train;
        intersections = intersectionName;
        segmentrs = segmentName;
        currentSpeed = speed;
    }

    public static AnnounceArrivalToIntersection create(RailwayTrain train, Queue<String> intersectionName, Queue<String> segment, float speed) {
        return new AnnounceArrivalToIntersection(train, intersectionName, segment, speed);
     }

    @Override
    public void action() {
        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message)) {
            if (segmentrs.isEmpty())
            {
                System.out.println("I have arrived at final station:" + intersections.remove());
                train.setSpeed(0);
            }
            else
            {
                System.out.println("sending message to next intersection:" + intersections.peek());
                final ACLMessage proposal = new ACLMessage(ACLMessage.INFORM);
                proposal.addReceiver(new AID(intersections.remove(), AID.ISLOCALNAME));
                proposal.setContent(currentSpeed.toString());
                myAgent.send(proposal);
            }
        }
    }
}
