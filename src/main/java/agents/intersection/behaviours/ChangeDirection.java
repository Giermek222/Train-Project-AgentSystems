package agents.intersection.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import model.RailwayIntersection;

import java.util.Objects;

import static jade.lang.acl.ACLMessage.AGREE;

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
            final ACLMessage response = new ACLMessage(ACLMessage.CONFIRM);
            final String segment = message.getContent();
            if (segment.equals("Final station"))
            {
                System.out.println("[" + myAgent.getLocalName() + "] This train will be stopping here ");
                response.setContent("final station");
            }
            else
            {
                intersection.setNextSegmentByName(segment);
                System.out.println("[" + myAgent.getLocalName() + "] Switching to " + segment);
                response.setContent("Not final station");
            }




            response.addReceiver(message.getSender());
            myAgent.send(response);


        }
    }
}
