package agents.planner;

import agents.AgentConstants;
import agents.planner.behaviours.HandleMalfunction;
import agents.planner.behaviours.SendNewRoute;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import planner.CentralizedPlanner;
import util.GraphDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlannerAgent extends Agent {
    private List<String> segments = new ArrayList<>();
    private List<String> costs = new ArrayList<>();

    @Override
    protected void setup() {
        super.setup();
        final Object[] params = getArguments();
        if (params.length < 2) {
            System.out.println("Usage [planner name ], [pairs of segments and costs separated by comas]");
            doDelete();
        }
        for (int i = 1; i < params.length; ++i) {
            if (i % 2 != 0)
                segments.add(params[i].toString());
            else
                costs.add(params[i].toString());
        }

        // register self as planner
        DFAgentDescription dfDesc = new DFAgentDescription ();
        dfDesc.setName (getAID ());

        ServiceDescription sd = new ServiceDescription ();
        sd.setName (AgentConstants.SERVICE_PLANNER);
        sd.setType (AgentConstants.SERVICE_PLANNER);

        dfDesc.addServices (sd);

        try {
            DFService.register (this, dfDesc);
        } catch (FIPAException exception) {
            exception.printStackTrace ();
        }
        Map<String, List<CentralizedPlanner.RouteDescription>> graphDescription = GraphDescriptor.describeRailway(segments, costs);
        CentralizedPlanner planner = new CentralizedPlanner(graphDescription);
        addBehaviour(HandleMalfunction.create(planner));
        addBehaviour(SendNewRoute.create(planner));
    }

    @Override
    protected void takeDown () {
        try {
            DFService.deregister (this);
        } catch (FIPAException fipaException) {
            fipaException.printStackTrace ();
        }

        super.takeDown ();
    }
}
