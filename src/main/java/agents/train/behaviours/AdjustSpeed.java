package agents.train.behaviours;

import agents.messageparams.IntersectionResponse;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.RailwayTrain;

import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

import static jade.lang.acl.ACLMessage.ACCEPT_PROPOSAL;
import static java.lang.Thread.sleep;

public class AdjustSpeed extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACCEPT_PROPOSAL);
    private final RailwayTrain train;
    private final Stack<String> intersections;
    private final Stack<String> segments;

    public AdjustSpeed(RailwayTrain train, Stack<String> intersections, Stack<String> segments) {

        this.train = train;
        this.intersections = intersections;
        this.segments = segments;
    }

    public static AdjustSpeed create( RailwayTrain train, Stack<String> intersections, Stack<String> segments) {
        return new AdjustSpeed(train, intersections, segments);
    }

    @Override
    public void action() {
        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message)) {
            try {
                final IntersectionResponse params = (IntersectionResponse) message.getContentObject();
                train.setSpeed(params.speed);

                sleep(params.time);
                myAgent.addBehaviour(AnnounceArrivalToIntersection.create(segments.pop(),intersections.pop(), train.getSpeed()));

            } catch (UnreadableException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
