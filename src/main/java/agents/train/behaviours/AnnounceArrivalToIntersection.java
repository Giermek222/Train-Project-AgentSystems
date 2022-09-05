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
            float current_speed = train.getSpeed();
            train.setSpeed(0);
            myAgent.doWait(1000);
            train.setSpeed(current_speed);

            myAgent.doWait(100);

            if (message.getContent().equals("final station"))
            {
                train.setSpeed(0);
            }
            else
            {
                System.out.println("sending message to next intersection:" + intersections.peek());
                final ACLMessage proposal = new ACLMessage(ACLMessage.INFORM);
                TrainToIntersectionInfo messageContent = new TrainToIntersectionInfo();
                messageContent.setMaxSpeed(train.getMaxSpeed());
                messageContent.setPreviousIntersection(train.getPreviousIntersection().getName());

                RailwayIntersection last = (RailwayIntersection) Simulation.getScene().getObject(intersections.peek());
                train.setPreviousIntersection(last);
                proposal.addReceiver(new AID(intersections.remove(), AID.ISLOCALNAME));

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
