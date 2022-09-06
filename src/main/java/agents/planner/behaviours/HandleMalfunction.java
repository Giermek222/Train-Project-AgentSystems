package agents.planner.behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import model.RailwayPlan;
import org.javatuples.Pair;
import util.SegmentParser;

import java.util.*;

import static jade.lang.acl.ACLMessage.INFORM;
import static jade.lang.acl.ACLMessage.PROPOSE;

public class HandleMalfunction extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(INFORM);

    private RailwayPlan plan;

    public static HandleMalfunction create(RailwayPlan railwayPlan) {
        return new HandleMalfunction(railwayPlan);
    }

    private HandleMalfunction(RailwayPlan railwayPlan) {
        plan = railwayPlan;
    }

    @Override
    public void action() {
        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message))
        {
            String brokenSegment = message.getContent();

            Pair<String, String> parsedSegment = SegmentParser.parse(brokenSegment);
            Map<String, List<String>> tmpPlan = plan.getPlan();
            tmpPlan.get(parsedSegment.getValue0()).remove(parsedSegment.getValue1());
            plan.setPlan(tmpPlan);

            List<AID> affectedTrains = null;
            try {
                affectedTrains = FindAffectedTrains(brokenSegment);
            } catch (FIPAException e) {
                throw new RuntimeException(e);
            }

            for (AID id : affectedTrains) {
                ACLMessage newRouteProposal = new ACLMessage(PROPOSE);
                newRouteProposal.addReceiver(id);
                newRouteProposal.setContent(brokenSegment);
                myAgent.send(newRouteProposal);
            }

        }
    }

    private List<AID> FindAffectedTrains(String broken_segment) throws FIPAException {
        final List<AID> affectedTrains = new ArrayList<>();
        final DFAgentDescription template = new DFAgentDescription();
        final ServiceDescription description = new ServiceDescription();
        description.setType("Passing");
        description.setName(broken_segment);
        template.addServices(description);
        final  DFAgentDescription[] agents = DFService.search(myAgent, template);
        Arrays.stream(agents).forEach(train -> affectedTrains.add(train.getName()));
        return affectedTrains;
    }



}
