package agents.train.helpers;

import agents.train.TrainAgent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import model.RailwayIntersection;
import model.RailwayTrain;
import model.messageparams.TrainToIntersectionInfo;
import simulation.Simulation;

import java.io.IOException;
import java.util.Queue;

public class SendMessageToIntersection {

    public void send(TrainAgent myAgent, Queue<String> segmentrs, Queue<String> intersections, RailwayTrain train) {
        myAgent.doWait(100);
            if (segmentrs.isEmpty())
            {

                RailwayIntersection finalIntersection = (RailwayIntersection) Simulation.getScene().getObject(intersections.remove());
                float distance = train.getLastIntersection().distance(finalIntersection.getPosition());
                myAgent.doWait((long) (1000 * distance/train.getSpeed()));
                System.out.println("I have arrived at final station:");
                train.setSpeed(0);

            }
            else
            {

                System.out.println("sending message to next intersection:" + intersections.peek());
                final ACLMessage proposal = new ACLMessage(ACLMessage.INFORM);
                RailwayIntersection last = (RailwayIntersection) Simulation.getScene().getObject(intersections.peek());
                train.setLastIntersection(last.getPosition());
                proposal.addReceiver(new AID(intersections.remove(), AID.ISLOCALNAME));

                TrainToIntersectionInfo messageContent = new TrainToIntersectionInfo();
                messageContent.setMaxSpeed(train.getMaxSpeed());
                messageContent.setSecondIntersection(intersections.peek());

                try {
                    proposal.setContentObject(messageContent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                myAgent.send(proposal);
            }

    }

}
