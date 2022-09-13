package agents.train.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.RailwayTrain;
import planner.CentralizedPlanner;

import java.util.*;

import static jade.lang.acl.ACLMessage.PROPAGATE;


public class ApplyNewRoute extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(PROPAGATE);

    RailwayTrain train;
    Queue<String> intersections;
    Queue<String> segemnts;


    public static ApplyNewRoute create(RailwayTrain train, Queue<String> route_intersections, Queue<String> route_segments) {
        return new ApplyNewRoute(train, route_intersections, route_segments);
    }

    public ApplyNewRoute(RailwayTrain train, Queue<String> route_intersections, Queue<String> route_segments) {
        this.train = train;
        this.intersections = route_intersections;
        this.segemnts = route_segments;
    }

    @Override
    public void action() {

        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message))
        {

            try {
                List<String> route = (List<String>) message.getContentObject();
                if (route.contains("Not_Found")) {
                    return; //this message is displayed if there is no route available for our train

                }
                intersections = new LinkedList<>(route);
                segemnts = new LinkedList<>(parseRoad(route));
                train.setRoadStable(true);
                train.setColor(0,255,0);
                myAgent.addBehaviour(StartRide.create(train, intersections, segemnts, train.getMaxSpeed()));
            } catch (UnreadableException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private  List<String> parseRoad(List<String> route) {
        List<String> segments = new ArrayList<>();
        for (int i = 0; i < route.size() - 1 ; ++i) {
            segments.add(
                    route.get(i).split("_")[1]
                    + "-" +
                    route.get(i + 1).split("_")[1]
            );
        }
        return segments;
    }
}