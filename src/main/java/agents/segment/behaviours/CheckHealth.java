package agents.segment.behaviours;

import agents.intersection.behaviours.ReceiveArrivalInfo;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import model.RailwayIntersection;
import model.RailwaySegment;

import static jade.lang.acl.ACLMessage.INFORM;

public class CheckHealth extends TickerBehaviour {
    private RailwaySegment segment;
    public CheckHealth(Agent a, long period, RailwaySegment simulationSegment) {
        super(a, period);
        segment = simulationSegment;
    }

    public static CheckHealth create(Agent agent, long period, RailwaySegment simulationSegment) {
        return new CheckHealth(agent, period, simulationSegment);
    }
    @Override
    protected void onTick() {

        if (segment.isBroken()) {
            System.out.println(segment.getName() + "broke down");
            ACLMessage message = new ACLMessage(INFORM);
            message.addReceiver(new AID("planner", AID.ISLOCALNAME));
            message.setContent(segment.getName());

            myAgent.send(message);
            myAgent.removeBehaviour(this);
        }
    }
}
