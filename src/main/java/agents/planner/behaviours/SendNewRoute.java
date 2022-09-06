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
import util.SegmentParser;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static jade.lang.acl.ACLMessage.*;

public class SendNewRoute extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(CONFIRM);

    private RailwayPlan plan;

    public static SendNewRoute create(RailwayPlan railwayPlan) {
        return new SendNewRoute(railwayPlan);
    }

    private SendNewRoute(RailwayPlan railwayPlan) {
        plan = railwayPlan;
    }
    @Override
    public void action() {

        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message))
        {
            try {
                TrainRerouteParams trainParams = (TrainRerouteParams) message.getContentObject();
                String newRoute = FindNewRoute(trainParams.getBeginning(), trainParams.getEnd(), plan.getPlan());

                ACLMessage routeProposal = new ACLMessage(PROPAGATE);
                routeProposal.addReceiver(message.getSender());
                routeProposal.setContent(newRoute);
                myAgent.send(routeProposal);
            } catch (UnreadableException e) {
                throw new RuntimeException(e);
            }



        }
    }

    private String FindNewRoute(String beginning, String End, Map<String, List<String>> route) {
        return "new route placeholder";
    }
}
