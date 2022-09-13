package agents.intersection;

import agents.AgentConstants;
import agents.intersection.behaviours.ChangeDirection;
import agents.intersection.behaviours.ReceiveArrivalInfo;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import model.RailwayIntersection;
import simulation.Simulation;

import java.util.ArrayList;
import java.util.List;

public class InsersectionAgent extends Agent {
    private List<String>  outgoing = new ArrayList<>();

    @Override
    protected void setup() {
        super.setup();

        final Object[] params = getArguments();
        if (params.length < 2) {
            System.out.println("Usage [intersection name ], [intersections separated by comas]");
            doDelete();
        }
        String intersectionName = params[0].toString();
        RailwayIntersection intersection = (RailwayIntersection) Simulation.getScene().getObject(intersectionName);
        for (int i = 1; i < params.length; ++i) {

            outgoing.add(params[i].toString());
        }

        // register self as intersection
        DFAgentDescription dfDesc = new DFAgentDescription ();
        dfDesc.setName (getAID ());

        ServiceDescription sd = new ServiceDescription ();
        sd.setName (AgentConstants.SERVICE_INTERSECTION);
        sd.setType (AgentConstants.SERVICE_INTERSECTION);

        dfDesc.addServices (sd);

        try {
            DFService.register (this, dfDesc);
        } catch (FIPAException exception) {
            exception.printStackTrace ();
        }

        addBehaviour(ReceiveArrivalInfo.create(intersection));
        addBehaviour(ChangeDirection.create(intersection));
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
