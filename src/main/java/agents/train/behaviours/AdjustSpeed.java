package agents.train.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.RailwayTrain;
import model.messageparams.IntersectionResponse;

import java.util.Objects;
import java.util.Queue;

import static jade.lang.acl.ACLMessage.ACCEPT_PROPOSAL;

public class AdjustSpeed extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACCEPT_PROPOSAL);
    private final RailwayTrain train;

    private final Queue<String> segments;

    public AdjustSpeed(RailwayTrain train, Queue<String> segments) {

        this.train = train;
        this.segments = segments;
    }

    public static AdjustSpeed create( RailwayTrain train,  Queue<String> segments) {
        return new AdjustSpeed(train, segments);
    }

    @Override
    public void action() {
        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message)) {
            try {
                System.out.println("They know I'm coming. Time to wait now");

                IntersectionResponse responseParams = (IntersectionResponse) message.getContentObject();
                train.setSpeed(responseParams.speed);
                long sleep_time = (long)(responseParams.time * 1000);
                myAgent.doWait(sleep_time);


                ACLMessage response =  new ACLMessage(ACLMessage.AGREE);
                response.addReceiver(message.getSender());
                if (segments.isEmpty())
                    response.setContent("Final station");
                else
                    response.setContent(segments.remove());
                myAgent.send(response);

            } catch (UnreadableException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
