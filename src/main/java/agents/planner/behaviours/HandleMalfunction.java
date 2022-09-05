package agents.planner.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Objects;

import static jade.lang.acl.ACLMessage.INFORM;

public class HandleMalfunction extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(INFORM);

    public static HandleMalfunction create() {
        return new HandleMalfunction();
    }

    private HandleMalfunction() {}

    @Override
    public void action() {
        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message))
        {
            String broken_segment = message.getContent();

        }
    }
}
