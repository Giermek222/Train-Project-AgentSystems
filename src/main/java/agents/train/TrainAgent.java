package agents.train;

import agents.train.behaviours.AnnounceArrivalToIntersection;
import jade.core.Agent;
import model.RailwayTrain;
import simulation.Simulation;

import java.util.ArrayDeque;
import java.util.Queue;

public class TrainAgent extends Agent {

    private RailwayTrain train;
    private final Queue<String> intersections = new ArrayDeque<>();
    private final Queue<String> segments = new ArrayDeque<>();

    @Override
    protected void setup() {
        super.setup();
        final Object[] params = getArguments();
        if (params.length != 3) {
            System.out.println("Usage [train name], [starting station], [destination]");
            doDelete();
        }
        String trainName = params[0].toString();

        train = (RailwayTrain) Simulation.getScene().getObject(trainName);
        //getRoute(params[1].toString(), params[2].toString());

        addBehaviour(AnnounceArrivalToIntersection.create(intersections.remove(), segments.remove(), train.getSpeed()));



    }
}
