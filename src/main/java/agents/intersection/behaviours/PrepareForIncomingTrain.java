package agents.intersection.behaviours;

import agents.messageparams.IntersectionResponse;
import agents.messageparams.TrainParams;
import agents.train.behaviours.AnnounceArrivalToIntersection;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.RailwayIntersection;
import org.javatuples.Pair;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jade.lang.acl.ACLMessage.*;

public class PrepareForIncomingTrain extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(PROPOSE);
    private final RailwayIntersection intersection;

    private final List<Pair<String, LocalDateTime>> scheduledTrains = new ArrayList<Pair<String, LocalDateTime>>();



    public PrepareForIncomingTrain(RailwayIntersection intersection) {
        this.intersection = intersection;
    }

    public static PrepareForIncomingTrain create(RailwayIntersection intersection) {
        return new PrepareForIncomingTrain(intersection);
    }

    @Override
    public void action() {
        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message)) {
            try {
                final TrainParams params = (TrainParams) message.getContentObject();
                intersection.setNextSegmentByName(params.segment);
                float arrivalTime = intersection.getLength() / params.speed;
                LocalDateTime time = LocalDateTime.now();
                time.plusSeconds((long) arrivalTime);
                scheduledTrains.add(new Pair<>(message.getSender().getName(), time));
                IntersectionResponse responseObject = new IntersectionResponse(params.speed, (long) arrivalTime);

                final ACLMessage response = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                response.addReceiver(message.getSender());
                response.setContentObject(responseObject);
                myAgent.send(response);

            } catch (UnreadableException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
