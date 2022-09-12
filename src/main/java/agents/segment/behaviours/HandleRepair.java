package agents.segment.behaviours;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import model.RailwaySegment;

import static jade.lang.acl.ACLMessage.INFORM;

public class HandleRepair extends TickerBehaviour {
    private RailwaySegment segment;

    public HandleRepair(Agent a, long period, RailwaySegment simulationSegment) {
        super(a, period);
        segment = simulationSegment;
    }

    public static HandleRepair create(Agent agent, long period, RailwaySegment simulationSegment) {
        return new HandleRepair(agent, period, simulationSegment);
    }
    @Override
    protected void onTick() {

        if (!segment.isBroken()) {
            System.out.println(segment.getName() + " was repaired");
            ACLMessage message = new ACLMessage(INFORM);
            message.addReceiver(new AID("planner", AID.ISLOCALNAME));
            message.setContent(segment.getName()  + "; got Repaired");
            myAgent.send(message);

            myAgent.addBehaviour(CheckHealth.create(myAgent, getPeriod(), segment));
            myAgent.removeBehaviour(this);
        }
    }
}