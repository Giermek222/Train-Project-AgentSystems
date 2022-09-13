package agents.intersection.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.RailwayIntersection;
import model.messageparams.TrainToIntersectionInfo;
import org.javatuples.Pair;
import org.joml.Vector2f;
import simulation.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static agents.AgentConstants.TRAIN_WINDOW;
import static jade.lang.acl.ACLMessage.INFORM;

public class ReceiveArrivalInfo extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(INFORM);
    private final RailwayIntersection intersection;

    private final List<Pair<String, Long>> scheduledTrains = new ArrayList<>();



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
                final TrainToIntersectionInfo info = (TrainToIntersectionInfo) message.getContentObject();

                RailwayIntersection secondIntersection = (RailwayIntersection) Simulation.getScene().getObject(info.getPreviousIntersection());

                Vector2f positionStart = intersection.getPosition();
                Vector2f positionEnd = secondIntersection.getPosition();

                final float distance = positionStart.distance(positionEnd);

                float arrivalTime;
                long time = 1;
                float i = 1;
                for (; i != 0; i *= 0.99) {
                    arrivalTime = distance / (info.getMaxSpeed() * i);
                    time = System.currentTimeMillis() + (long) (arrivalTime * 1000);
                    if (checkForCollision(time))
                        break;
                }
                scheduledTrains.add(new Pair<>(message.getSender().getName(), time));
                final ACLMessage response = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                response.addReceiver(message.getSender());
                response.setContent(Float.toString(info.getMaxSpeed() * i));
                myAgent.send(response);

            } catch (UnreadableException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean checkForCollision(final long time) {
        return scheduledTrains.stream().filter(train -> time - TRAIN_WINDOW <= train.getValue1() || train.getValue1() >= time + TRAIN_WINDOW).toList().isEmpty();
    }

}
