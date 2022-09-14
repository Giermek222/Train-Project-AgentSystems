package agents.train.behaviours;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import model.RailwayIntersection;
import model.RailwayTrain;
import model.messageparams.TrainPlannerRegisterParams;
import model.messageparams.TrainToIntersectionInfo;
import simulation.Simulation;

import java.io.IOException;
import java.io.Serializable;

import static jade.lang.acl.ACLMessage.ACCEPT_PROPOSAL;
import static simulation.Simulation.getScene;

public class StartRide extends OneShotBehaviour {

    private final RailwayTrain train;

    public StartRide( RailwayTrain train) {

        this.train = train;
    }

    public static StartRide create(RailwayTrain train) {
        return new StartRide( train);
    }

    @Override
    public void action() {
;
        try {
            ACLMessage message = new ACLMessage(ACCEPT_PROPOSAL);
            message.addReceiver(new AID("planner", AID.ISLOCALNAME));
            TrainPlannerRegisterParams plannerParams = new TrainPlannerRegisterParams(train.intersections.stream().toList(), train.getName(), train.getMaxSpeed());
            message.setContentObject(plannerParams);
            myAgent.send(message);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        train.setRoadStable(true);

        RailwayIntersection intersection = (RailwayIntersection) Simulation.getScene().getObject(train.intersections.remove());
        train.setPreviousIntersection(intersection);
        intersection.setNextSegmentByName(train.segments.remove());

        System.out.println("sending message to next intersection:" + train.intersections.peek());
            final ACLMessage proposal = new ACLMessage(ACLMessage.INFORM);
            TrainToIntersectionInfo messageContent = new TrainToIntersectionInfo();
            messageContent.setMaxSpeed(train.getMaxSpeed());
            messageContent.setPreviousIntersection(train.getPreviousIntersection().getName());
            train.setPreviousIntersection((RailwayIntersection) getScene().getObject(train.intersections.peek()));

            proposal.addReceiver(new AID(train.intersections.remove(), AID.ISLOCALNAME));
            try {
                proposal.setContentObject(messageContent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            myAgent.send(proposal);

    }
}
