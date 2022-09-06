package agents.train.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.javatuples.Pair;
import util.SegmentParser;

import java.util.Objects;

import static jade.lang.acl.ACLMessage.*;

public class AcknowledgeReroute extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(PROPOSE);

    @Override
    public void action() {

        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message))
        {
        }
    }
}
