package agents.planner.behaviours;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.javatuples.Pair;
import planner.CentralizedPlanner;
import util.SegmentParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static agents.AgentConstants.INTERSECTION_PREFIX;
import static agents.AgentConstants.TRAIN_DESCRIPTION;
import static jade.lang.acl.ACLMessage.INFORM;
import static jade.lang.acl.ACLMessage.PROPOSE;

public class HandleMalfunction extends CyclicBehaviour {

    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(INFORM);

    private CentralizedPlanner plan;

    public static HandleMalfunction create(CentralizedPlanner railwayPlan) {
        return new HandleMalfunction(railwayPlan);
    }

    private HandleMalfunction(CentralizedPlanner railwayPlan) {
        plan = railwayPlan;
    }

    @Override
    public void action() {
        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message))
        {
            String[] information = message.getContent().split(";");
            String affactedSegment = information[0];
            String status = information[1];
            Pair<String, String> parsedSegment = SegmentParser.parseFullName(affactedSegment);
            if (status.contains("Broken"))
                plan.notifyRouteBroken(INTERSECTION_PREFIX + parsedSegment.getValue0(), INTERSECTION_PREFIX + parsedSegment.getValue1());
            else
                plan.notifyRouteRepaired(INTERSECTION_PREFIX + parsedSegment.getValue0(), INTERSECTION_PREFIX + parsedSegment.getValue1());

            List<AID> affectedTrains = null;
            try {
                affectedTrains = findAffectedTrains(parsedSegment.getValue0() + "-" + parsedSegment.getValue1());
            }
            catch (FIPAException e)
            {
                throw new RuntimeException(e);
            }

            for (AID id : affectedTrains) {
                ACLMessage newRouteProposal = new ACLMessage(PROPOSE);
                newRouteProposal.addReceiver(id);
                newRouteProposal.setContent(parsedSegment.getValue0() + "-" + parsedSegment.getValue1());
                myAgent.send(newRouteProposal);
            }

        }
    }

    private List<AID> findAffectedTrains(String brokenSegment) throws FIPAException {
        final List<AID> affectedTrains = new ArrayList<>();
        final DFAgentDescription template = new DFAgentDescription();
        final ServiceDescription description = new ServiceDescription();
        description.setType(TRAIN_DESCRIPTION);
        description.setName(brokenSegment);
        template.addServices(description);
        final  DFAgentDescription[] agents = DFService.search(myAgent, template);
        Arrays.stream(agents).forEach(train -> affectedTrains.add(train.getName()));
        return affectedTrains;
    }



}
