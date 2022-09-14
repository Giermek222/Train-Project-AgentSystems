package agents.planner.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.RailwayTrain;
import model.messageparams.TrainPlannerRegisterParams;
import planner.CentralizedPlanner;

import java.util.Objects;

import static jade.lang.acl.ACLMessage.ACCEPT_PROPOSAL;

public class AcceptTrainRoute extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACCEPT_PROPOSAL);

    private CentralizedPlanner plan;

    public static AcceptTrainRoute create(CentralizedPlanner railwayPlan) {
        return new AcceptTrainRoute(railwayPlan);
    }

    private AcceptTrainRoute(CentralizedPlanner railwayPlan) {
        plan = railwayPlan;
    }
    @Override
    public void action() {

        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message))
        {
            try {
                TrainPlannerRegisterParams train = (TrainPlannerRegisterParams) message.getContentObject();
                plan.acceptRoute(train.getRoute(), train.getName(), train.getMaxSpeed());
            } catch (UnreadableException e) {
                throw new RuntimeException(e);
            }


        }
    }
}