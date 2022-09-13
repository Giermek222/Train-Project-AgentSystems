package agents.planner.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.RailwayTrain;
import planner.CentralizedPlanner;

import java.util.Objects;

import static jade.lang.acl.ACLMessage.INFORM_IF;

public class HandleTrainArrival extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(INFORM_IF);

    private CentralizedPlanner plan;

    public static HandleTrainArrival create(CentralizedPlanner railwayPlan) {
        return new HandleTrainArrival(railwayPlan);
    }

    private HandleTrainArrival(CentralizedPlanner railwayPlan) {
        plan = railwayPlan;
    }
    @Override
    public void action() {

        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message))
        {
            String trainName =  message.getContent();
            plan.notifyTrainArrived(trainName);
        }
    }
}