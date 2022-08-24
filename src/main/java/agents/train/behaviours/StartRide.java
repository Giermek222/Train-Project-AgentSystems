package agents.train.behaviours;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import model.RailwayTrain;

import java.util.Queue;

public class StartRide extends OneShotBehaviour {


    private final Queue<String> intersections;
    private final Queue<String> segmentrs;
    private final Float currentSpeed;

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
        if (segmentrs.isEmpty())
        {
            System.out.println("I have arrived at final station:" + intersections.remove());
        }
        else
        {
            System.out.println("sending message to next intersection:" + intersections.peek());
            final ACLMessage proposal = new ACLMessage(ACLMessage.INFORM);
            proposal.addReceiver(new AID(intersections.remove(), AID.ISLOCALNAME));
            proposal.setContent(currentSpeed.toString());
            myAgent.send(proposal);
        }
    }
}
