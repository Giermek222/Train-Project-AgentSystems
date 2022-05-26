package agents.train;

import agents.train.behaviours.AnnounceArrivalToIntersection;
import jade.core.Agent;
import model.RailwayTrain;
import simulation.Simulation;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class TrainAgent extends Agent {

    private RailwayTrain train;
    private final Queue<String> intersections = new ArrayDeque<>();
    private final Queue<String> segments = new ArrayDeque<>();

    @Override
    protected void setup() {
        super.setup();
        final Object[] params = getArguments();
        if (params.length < 4) {
            System.out.println("Usage [train name], [starting station], [pair <segment name, intersection name>]");
            doDelete();
        }
        String trainName = params[0].toString();
        train = (RailwayTrain) Simulation.getScene().getObject(trainName);
        for (int i = 1; i < params.length; ++i) {
            if (i % 2 != 0)
                intersections.add(params[i].toString());
            else
                segments.add(params[i].toString());
        }

        addBehaviour(AnnounceArrivalToIntersection.create(intersections.remove(), segments.remove(), train.getSpeed()));



    }
}
