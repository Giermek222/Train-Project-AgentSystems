package agents.train.behaviours;

import model.messageparams.IntersectionResponse;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.RailwayTrain;

import java.util.Objects;
import java.util.Queue;

import static jade.lang.acl.ACLMessage.ACCEPT_PROPOSAL;
import static java.lang.Thread.sleep;

public class AdjustSpeed extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACCEPT_PROPOSAL);
    private final RailwayTrain train;
private final Queue<String> intersections;
    private final Queue<String> segments;

    public AdjustSpeed(RailwayTrain train, Queue<String> intersections, Queue<String> segments) {

        this.train = train;
        this.intersections = intersections;
        this.segments = segments;
    }

    public static AdjustSpeed create( RailwayTrain train, Queue<String> intersections, Queue<String> segments) {
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
                if (segments.isEmpty() == false && intersections.isEmpty() == false)
                {
                    myAgent.addBehaviour(AnnounceArrivalToIntersection.create(segments.remove(),intersections.remove(), train.getSpeed()));
                }
                else
                {
                    myAgent.doDelete();
                }

            } catch (UnreadableException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
