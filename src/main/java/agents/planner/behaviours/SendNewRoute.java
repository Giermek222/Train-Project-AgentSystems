package agents.planner.behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import model.RailwayPlan;
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
            String route = message.getContent();

            Pair<String, String> parsedSegment = SegmentParser.parse(route);
            String newRoute = FindNewRoute(parsedSegment.getValue0(), parsedSegment.getValue1(), plan.getPlan());

            ACLMessage routeProposal = new ACLMessage(PROPAGATE);
            routeProposal.addReceiver(message.getSender());
            routeProposal.setContent(newRoute);
            myAgent.send(routeProposal);

        }
    }

    private String FindNewRoute(String beginning, String End, Map<String, List<String>> route) {
        return "new route placeholder";
    }
}
