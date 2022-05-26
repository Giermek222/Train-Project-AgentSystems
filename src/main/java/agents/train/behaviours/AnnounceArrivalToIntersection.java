package agents.train.behaviours;

import agents.messageparams.TrainParams;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class AnnounceArrivalToIntersection extends OneShotBehaviour {

   private final String nextIntersection;
   private final String nextSegment;
   private final Float currentSpeed;

    public AnnounceArrivalToIntersection(String intersectionName, String segmentName, float speed) {
        nextIntersection = intersectionName;
        nextSegment = segmentName;
        currentSpeed = speed;
    }

    public static AnnounceArrivalToIntersection create(String intersectionName, String segment, float speed) {
        return new AnnounceArrivalToIntersection(intersectionName, segment, speed);
     }

    @Override
    public void action() {
        final ACLMessage proposal = new ACLMessage(ACLMessage.PROPOSE);
        proposal.addReceiver(new AID(nextIntersection, AID.ISLOCALNAME));
        TrainParams params = new TrainParams(nextSegment, currentSpeed);

        try {
            proposal.setContentObject(params);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        myAgent.send(proposal);
    }
}
