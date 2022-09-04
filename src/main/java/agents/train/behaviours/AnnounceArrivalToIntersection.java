package agents.train.behaviours;

import agents.train.TrainAgent;
import agents.train.helpers.SendMessageToIntersection;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import model.RailwayTrain;
import model.messageparams.TrainToIntersectionInfo;

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

   private final SendMessageToIntersection sendMessageToIntersection = new SendMessageToIntersection();
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
            sendMessageToIntersection.send((TrainAgent) myAgent, segmentrs, intersections, train);
        }
    }
}
