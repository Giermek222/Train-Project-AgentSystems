package agents.intersection.behaviours;

import model.messageparams.IntersectionResponse;
import model.messageparams.TrainParams;
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

public class ReceiveArrivalInfo extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(INFORM);
    private final RailwayIntersection intersection;

    private final List<Pair<String, LocalDateTime>> scheduledTrains = new ArrayList<Pair<String, LocalDateTime>>();



    public ReceiveArrivalInfo(RailwayIntersection intersection) {
        this.intersection = intersection;
    }

    public static ReceiveArrivalInfo create(RailwayIntersection intersection) {
        return new ReceiveArrivalInfo(intersection);
    }

    @Override
    public void action() {
        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message)) {
            try {
                System.out.println("[" + message.getSender() + "] is approaching me");
                final String speed =  message.getContent();

                float arrivalTime = intersection.getLength() / Float.parseFloat(speed);
                LocalDateTime time = LocalDateTime.now();
                scheduledTrains.add(new Pair<>(message.getSender().getName(), time));
                IntersectionResponse responseObject = new IntersectionResponse(Float.parseFloat(speed), (long) arrivalTime);


                final ACLMessage response = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                response.addReceiver(message.getSender());
                response.setContentObject(responseObject);
                myAgent.send(response);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
