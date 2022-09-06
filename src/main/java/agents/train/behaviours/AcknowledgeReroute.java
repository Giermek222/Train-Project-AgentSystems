package agents.train.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import model.RailwayTrain;
import model.messageparams.TrainRerouteParams;
import org.javatuples.Pair;
import util.SegmentParser;

import java.io.IOException;
import java.util.Objects;
import java.util.Queue;

import static jade.lang.acl.ACLMessage.*;

public class AcknowledgeReroute extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(PROPOSE);

    private final RailwayTrain train;

    private Queue<String> segments;
    private Queue<String> intersections;

    public AcknowledgeReroute(RailwayTrain train, Queue<String> segments, Queue<String> intersections) {

        this.train = train;
        this.segments = segments;
        this.intersections = intersections;
    }

    public static AcknowledgeReroute create( RailwayTrain train,  Queue<String> segments, Queue<String> intersections) {
        return new AcknowledgeReroute(train, segments, intersections);
    }


    @Override
    public void action() {

        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message))
        {
            final String brokenIntersection = message.getContent();

            if (segments.contains(brokenIntersection))
            {
                final float currentSpeed = train.getSpeed();
                final String beginning = intersections.remove();
                String end = null;
                while (!intersections.isEmpty()) {
                    end = intersections.remove();
                }
                TrainRerouteParams responseParams = new TrainRerouteParams(currentSpeed, train.getMaxSpeed(), beginning, end);
                ACLMessage response = new ACLMessage(CONFIRM);

                try {
                    response.setContentObject(responseParams);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                response.addReceiver(message.getSender());
                myAgent.send(response);

            }
        }
    }
}
