package util;

import model.RailwayIntersection;
import model.RailwaySegment;
import model.RailwayTrain;
import org.javatuples.Pair;
import org.joml.Vector2f;
import simulation.Simulation;
import simulation.SimulationScene;
import util.SegmentParser;

public class ScenarioRunner {

    public static void RunScenario1() {
        SimulationScene scene = Simulation.getScene ();

        final RailwayIntersection[] intersections = {
                new RailwayIntersection ("intersection_1", new Vector2f(200, 300)),
                new RailwayIntersection ("intersection_2", new Vector2f (200, 100)),
                new RailwayIntersection ("intersection_3", new Vector2f (600, 300)),
                new RailwayIntersection ("intersection_4", new Vector2f (800, 100)),
                new RailwayIntersection ("intersection_5", new Vector2f (100, 500)),
                new RailwayIntersection ("intersection_6", new Vector2f (400, 300)),
                new RailwayIntersection ("intersection_7", new Vector2f (600, 500)),
                new RailwayIntersection ("intersection_8", new Vector2f (800, 500)),
                new RailwayIntersection ("intersection_9", new Vector2f (250, 600))
        };

        final String[] segments = { "1-2", "2-3", "3-4", "1-6", "3-6", "3-7", "4-8", "5-1", "5-6", "6-7", "7-8", "7-9", "8-3", "9-5" };

        for (String segment : segments) {
            Pair<String, String> parsed = SegmentParser.parse(segment);
            String from = parsed.getValue0(), to = parsed.getValue1();
            Simulation.getScene ().addObject (new RailwaySegment(
                    String.format("segment_%s", segment),
                    intersections[ Integer.parseInt(from) - 1],
                    intersections[Integer.parseInt(to) - 1]
            ));
        }

        for (RailwayIntersection intersection : intersections) {
            Simulation.getScene ().addObject (intersection);
        }

        RailwayTrain train = new RailwayTrain ("train_1", 100.0f, intersections[2]);
        train.setSpeed (0.0f);
        train.setColor (0, 255, 0);

        RailwayTrain train2 = new RailwayTrain ("train_2", 100.0f, intersections[0]);
        train2.setSpeed (0.0f);
        train2.setColor (0, 255, 0);

        Simulation.getScene ().addObject (train);
        Simulation.getScene ().addObject (train2);
    }
}
