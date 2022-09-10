package agents.planner;

import agents.AgentConstants;
import agents.planner.behaviours.HandleMalfunction;
import agents.planner.behaviours.SendNewRoute;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import model.RailwayPlan;
import planner.CentralizedPlanner;

import java.util.ArrayList;
import java.util.List;

public class PlannerAgent extends Agent {
    private List<String> segments = new ArrayList<>();

    @Override
    protected void setup() {
        super.setup();
        final Object[] params = getArguments();
        if (params.length < 2) {
            System.out.println("Usage [planner name ], [segments separated by comas]");
            doDelete();
        }
        for (int i = 1; i < params.length; ++i) {
            segments.add(params[i].toString());
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

        RailwayPlan plan = new RailwayPlan(segments);
        addBehaviour(HandleMalfunction.create(plan));
        addBehaviour(SendNewRoute.create(plan));
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
