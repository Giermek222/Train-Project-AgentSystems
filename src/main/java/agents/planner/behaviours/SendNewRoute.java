package agents.planner.behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.RailwayPlan;
import model.messageparams.TrainRerouteParams;
import org.javatuples.Pair;
import planner.CentralizedPlanner;
import util.SegmentParser;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static jade.lang.acl.ACLMessage.*;

public class SendNewRoute extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(CONFIRM);

    private CentralizedPlanner plan;

    public static SendNewRoute create(CentralizedPlanner railwayPlan) {
        return new SendNewRoute(railwayPlan);
    }

    private SendNewRoute(CentralizedPlanner railwayPlan) {
        plan = railwayPlan;
    }
    @Override
    public void action() {

        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message))
        {
            try {
                TrainRerouteParams trainParams = (TrainRerouteParams) message.getContentObject();
                //TODO add different priorities per train
                List<String> newRoute = plan.findRoute(trainParams.getBeginning(), trainParams.getEnd(), trainParams.getMaxSpeed(), trainParams.getPriority());
                ACLMessage routeProposal = new ACLMessage(PROPAGATE);
                routeProposal.addReceiver(message.getSender());
                routeProposal.setContentObject((Serializable) newRoute);
                myAgent.send(routeProposal);
            } catch (UnreadableException | IOException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
