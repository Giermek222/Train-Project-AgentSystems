package agents.intersection.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.RailwayIntersection;
import model.messageparams.IntersectionResponse;
import model.messageparams.TrainParams;
import org.javatuples.Pair;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static jade.lang.acl.ACLMessage.AGREE;
import static jade.lang.acl.ACLMessage.INFORM;

public class ChangeDirection extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(AGREE);

    private final RailwayIntersection intersection;

    public ChangeDirection(RailwayIntersection intersection) {
        this.intersection = intersection;
    }

    public static ChangeDirection create(RailwayIntersection intersection) {
        return new ChangeDirection(intersection);
    }

    @Override
    public void action() {
        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message)) {
            final String segment = message.getContent();
            intersection.setNextSegmentByName(segment);

            System.out.println("[" + myAgent.getLocalName() + "] Switching to " + segment);
            final ACLMessage response = new ACLMessage(ACLMessage.CONFIRM);
            response.addReceiver(message.getSender());
            myAgent.send(response);

        }
    }
}
