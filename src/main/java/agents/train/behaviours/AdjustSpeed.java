package agents.train.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import model.RailwayTrain;

import java.util.Objects;

import static agents.AgentConstants.FINAL_STATION;
import static jade.lang.acl.ACLMessage.ACCEPT_PROPOSAL;

public class AdjustSpeed extends CyclicBehaviour {
    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACCEPT_PROPOSAL);
    private final RailwayTrain train;

    public AdjustSpeed(RailwayTrain train) {
        this.train = train;
    }

    public static AdjustSpeed create( RailwayTrain train) {
        return new AdjustSpeed(train);
    }

    @Override
    public void action() {
        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message)) {
            System.out.println("They know I'm coming. Time to wait now");

            String speed =  message.getContent();
            train.setSpeed(Float.parseFloat(speed));

            while (!train.isTraversingSegment()) {System.out.print("");}
            while (train.isTraversingSegment()) {System.out.print("");}

            ACLMessage response =  new ACLMessage(ACLMessage.AGREE);
            response.addReceiver(message.getSender());
            if (train.segments.isEmpty())
                response.setContent(FINAL_STATION);
            else
                response.setContent(train.segments.remove());
            myAgent.send(response);

        }
    }
}
