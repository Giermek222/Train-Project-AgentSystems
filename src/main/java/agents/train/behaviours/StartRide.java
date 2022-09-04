package agents.train.behaviours;

import agents.train.TrainAgent;
import agents.train.helpers.SendMessageToIntersection;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import model.RailwayIntersection;
import model.RailwayTrain;
import model.messageparams.TrainToIntersectionInfo;
import simulation.Simulation;

import java.io.IOException;
import java.util.Queue;

import static simulation.Simulation.getScene;

public class StartRide extends OneShotBehaviour {


    private final Queue<String> intersections;
    private final Queue<String> segmentrs;
    private final Float currentSpeed;


    private final SendMessageToIntersection sendMessageToIntersection = new SendMessageToIntersection();


    private final RailwayTrain train;

    public StartRide(Queue<String> intersectionName, Queue<String> segmentName, float speed, RailwayTrain train) {
        intersections = intersectionName;
        segmentrs = segmentName;
        currentSpeed = speed;
        this.train = train;
    }

    public static StartRide create(RailwayTrain train, Queue<String> intersectionName, Queue<String> segment, float speed) {
        return new StartRide(intersectionName, segment, speed, train);
    }

    @Override
    public void action() {
        RailwayIntersection intersection = (RailwayIntersection) Simulation.getScene().getObject(intersections.remove());
        train.setLastIntersection(intersection.getPosition());
        intersection.setNextSegmentByName(segmentrs.remove());
        train.setSpeed(train.getMaxSpeed());
        sendMessageToIntersection.send((TrainAgent) myAgent, segmentrs, intersections, train);
    }
}
