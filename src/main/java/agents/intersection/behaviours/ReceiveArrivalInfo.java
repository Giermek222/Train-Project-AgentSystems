package agents.intersection.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.RailwayIntersection;
import model.messageparams.IntersectionResponse;
import model.messageparams.TrainToIntersectionInfo;
import org.javatuples.Pair;
import org.joml.Vector2f;
import simulation.Simulation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jade.lang.acl.ACLMessage.INFORM;

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
                final TrainToIntersectionInfo info = (TrainToIntersectionInfo)message.getContentObject() ;

                RailwayIntersection secondIntersection = (RailwayIntersection) Simulation.getScene().getObject(info.getSecondIntersection());

                Vector2f positionStart = intersection.getPosition();
                Vector2f positionEnd = secondIntersection.getPosition();

                final float distance = positionStart.distance(positionEnd);

                float arrivalTime = distance / info.getMaxSpeed();
                LocalDateTime time = LocalDateTime.now();
                scheduledTrains.add(new Pair<>(message.getSender().getName(), time));
                IntersectionResponse responseObject = new IntersectionResponse(info.getMaxSpeed(),  arrivalTime);


                final ACLMessage response = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                response.addReceiver(message.getSender());
                response.setContentObject(responseObject);
                myAgent.send(response);

            } catch (IOException | UnreadableException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
