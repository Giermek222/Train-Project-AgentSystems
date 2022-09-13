package util;

import model.RailwayIntersection;
import model.RailwaySegment;
import model.RailwayTrain;
import org.javatuples.Pair;
import org.joml.Vector2f;
import simulation.Simulation;

public class ScenarioRunner {

    public static void RunScenario1() {

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
        Simulation.getScene ().addObject (train);

        RailwayTrain train2 = new RailwayTrain ("train_2", 100.0f, intersections[0]);
        train2.setSpeed (0.0f);
        train2.setColor (0, 255, 0);
        Simulation.getScene ().addObject (train2);
    }

    public static void RunScenario2() {

        final RailwayIntersection[] intersections = {
                new RailwayIntersection ("intersection_1", new Vector2f(600, 200)),
                new RailwayIntersection ("intersection_2", new Vector2f (600, 400)),
                new RailwayIntersection ("intersection_3", new Vector2f (250, 400)),
                new RailwayIntersection ("intersection_4", new Vector2f (300, 500)),
                new RailwayIntersection ("intersection_5", new Vector2f (600, 750)),
                new RailwayIntersection ("intersection_6", new Vector2f (900, 500)),
                new RailwayIntersection ("intersection_7", new Vector2f (950, 400)),


        };
        final String[] segments = { "2-1", "1-2", "3-2", "4-2", "5-2", "6-2", "7-2", "2-3", "2-4", "2-5", "2-6", "2-7"};

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

        RailwayTrain train2 = new RailwayTrain ("train_2", 100.0f, intersections[3]);
        train2.setSpeed (0.0f);
        train2.setColor (0, 255, 0);

        RailwayTrain train3 = new RailwayTrain ("train_3", 100.0f, intersections[4]);
        train3.setSpeed (0.0f);
        train3.setColor (0, 255, 0);

        RailwayTrain train4 = new RailwayTrain ("train_4", 100.0f, intersections[5]);
        train4.setSpeed (0.0f);
        train4.setColor (0, 255, 0);

        RailwayTrain train5 = new RailwayTrain ("train_5", 100.0f, intersections[6]);
        train5.setSpeed (0.0f);
        train5.setColor (0, 255, 0);

        Simulation.getScene ().addObject (train);
        Simulation.getScene ().addObject (train2);
        Simulation.getScene ().addObject (train3);
        Simulation.getScene ().addObject (train4);
        Simulation.getScene ().addObject (train5);
    }

    public static void RunScenario3() {
        final RailwayIntersection[] intersections = {
                new RailwayIntersection ("intersection_1", new Vector2f(100, 360)),
                new RailwayIntersection ("intersection_2", new Vector2f (300, 380)),
                new RailwayIntersection ("intersection_3", new Vector2f (500, 400)),
                new RailwayIntersection ("intersection_4", new Vector2f (600, 200)),
                new RailwayIntersection ("intersection_5", new Vector2f (500, 600)),
                new RailwayIntersection ("intersection_6", new Vector2f (700, 600)),
                new RailwayIntersection ("intersection_7", new Vector2f (700, 400)),
                new RailwayIntersection ("intersection_8", new Vector2f (900, 420)),
        };
        final String[] segments = { "2-1", "1-2", "3-2", "2-3", "3-4", "4-3", "3-7", "4-7", "7-4", "3-5", "5-3", "5-6", "6-5", "6-7", "7-6", "7-8", "8-7"};

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

        RailwayTrain train = new RailwayTrain ("train_1", 100.0f, intersections[0]);
        train.setSpeed (0.0f);
        train.setColor (0, 255, 0);
        Simulation.getScene ().addObject (train);
    }
}
