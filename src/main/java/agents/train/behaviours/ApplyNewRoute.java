package agents.train.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.RailwayTrain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static agents.AgentConstants.TRAIN_DESCRIPTION;
import static jade.lang.acl.ACLMessage.PROPAGATE;


public class ApplyNewRoute extends CyclicBehaviour {
    private final MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(PROPAGATE);
    RailwayTrain train;
    DFAgentDescription oldDescription;

    public static ApplyNewRoute create(RailwayTrain train, DFAgentDescription description) {
        return new ApplyNewRoute(train, description);
    }

    public ApplyNewRoute(RailwayTrain train, DFAgentDescription desc) {
        this.train = train;
        oldDescription = desc;

    }

    @Override
    public void action() {

        final ACLMessage message = myAgent.receive(messageTemplate);

        if (Objects.nonNull(message))
        {

            try {
                List<String> route = (List<String>) message.getContentObject();
                if (route.contains("Not_Found")) {
                    return; //this message is displayed if there is no route available for our train
                }

                train.intersections = new LinkedList<>(route);
                train.segments = new LinkedList<>(parseRoad(route));
                train.setColor(0,255,0);
                myAgent.addBehaviour(StartRide.create(train));

                DFService.deregister(myAgent, oldDescription);

                final DFAgentDescription description = new DFAgentDescription();
                description.setName(myAgent.getAID());

                for (String segment : train.segments) {
                    final ServiceDescription serviceDescription = new ServiceDescription();
                    serviceDescription.setType(TRAIN_DESCRIPTION);
                    serviceDescription.setName(segment);
                    description.addServices(serviceDescription);

                }
                DFService.register(myAgent, description);
                oldDescription = description;
            } catch (UnreadableException | FIPAException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private  List<String> parseRoad(List<String> route) {
        List<String> segments = new ArrayList<>();
        for (int i = 0; i < route.size() - 1 ; ++i) {
            segments.add(
                    route.get(i).split("_")[1]
                    + "-" +
                    route.get(i + 1).split("_")[1]
            );
        }
        return segments;
    }
}