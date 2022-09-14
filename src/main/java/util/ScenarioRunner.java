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

        final String[] segments = { "1-2", "2-3", "3-4", "1-6", "3-6", "3-7", "4-8", "5-1", "5-6", "6-7", "7-8", "7-9", "8-3", "9-5", "8-7", "7-3" };


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

    public static void RunScenario4() {
        final RailwayIntersection[] intersections = {
                new RailwayIntersection ("intersection_1", new Vector2f(100, 400)),
                new RailwayIntersection ("intersection_2", new Vector2f (300, 600)),
                new RailwayIntersection ("intersection_3", new Vector2f (300, 400)),
                new RailwayIntersection ("intersection_4", new Vector2f (600, 200)),
                new RailwayIntersection ("intersection_5", new Vector2f (900, 400)),
                new RailwayIntersection ("intersection_6", new Vector2f (600, 600)),
        };
        final String[] segments = { "3-1", "1-3", "3-2", "2-3", "3-4", "4-5", "3-5", "5-6", "3-6", "6-5"};

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

        RailwayTrain train2 = new RailwayTrain ("train_2", 100.0f, intersections[1]);
        train2.setSpeed (0.0f);
        train2.setColor (0, 255, 0);
        Simulation.getScene ().addObject (train2);
    }

    public static void RunScenario0() {
        final RailwayIntersection[] intersections = {
                new RailwayIntersection ("intersection_1", new Vector2f (200, 100)),
                new RailwayIntersection ("intersection_2", new Vector2f (400, 100)),
                new RailwayIntersection ("intersection_3", new Vector2f (600, 100)),
                new RailwayIntersection ("intersection_4", new Vector2f (800, 100)),
                new RailwayIntersection ("intersection_5", new Vector2f (1000, 100)),
                new RailwayIntersection ("intersection_6", new Vector2f (200, 300)),
                new RailwayIntersection ("intersection_7", new Vector2f (400, 300)),
                new RailwayIntersection ("intersection_8", new Vector2f (600, 300)),
                new RailwayIntersection ("intersection_9", new Vector2f (800, 300)),
                new RailwayIntersection ("intersection_10", new Vector2f (1000, 300)),
                new RailwayIntersection ("intersection_11", new Vector2f (200, 500)),
                new RailwayIntersection ("intersection_12", new Vector2f (400, 500)),
                new RailwayIntersection ("intersection_13", new Vector2f (600, 500)),
                new RailwayIntersection ("intersection_14", new Vector2f (800, 500)),
                new RailwayIntersection ("intersection_15", new Vector2f (1000, 500)),
                new RailwayIntersection ("intersection_16", new Vector2f (200, 700)),
                new RailwayIntersection ("intersection_17", new Vector2f (400, 700)),
                new RailwayIntersection ("intersection_18", new Vector2f (600, 700)),
                new RailwayIntersection ("intersection_19", new Vector2f (800, 700)),
                new RailwayIntersection ("intersection_20", new Vector2f (1000, 700)),

        };
        final String[] segments = {
                "1-2", "1-6", "1-7",
                "2-1", "2-3", "2-6", "2-7", "2-8",
                "3-2", "3-4", "3-7", "3-8", "3-9",
                "4-3", "4-5", "4-8", "4-9", "4-10",
                "5-4", "5-9", "5-10",
                "6-1", "6-2", "6-7", "6-11", "6-12",
                "7-1", "7-2", "7-3", "7-6", "7-8", "7-11", "7-12", "7-13",
                "8-2", "8-3", "8-4", "8-7", "8-9", "8-12", "8-13", "8-14",
                "9-3", "9-4", "9-5", "9-8", "9-10", "9-13", "9-14", "9-15",
                "10-4", "10-5", "10-9", "10-14", "10-15",
                "11-6", "11-7", "11-12", "11-16", "11-17",
                "12-6", "12-7", "12-8", "12-11", "12-13", "12-15", "12-16", "12-17",
                "13-7", "13-8", "13-9", "13-12", "13-14", "13-17", "13-18", "13-19",
                "14-8", "14-9", "14-10", "14-13", "14-15", "14-18", "14-19", "14-20",
                "15-9", "15-10", "15-14", "15-19", "15-20",
                "16-11", "16-12", "16-17",
                "17-11", "17-12", "17-13", "17-16", "17-18",
                "18-12", "18-13", "18-14", "18-17", "18-19",
                "19-13", "19-14", "19-15", "19-18", "19-20",
                "20-14", "20-15", "20-19"

        };

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

        RailwayTrain train2 = new RailwayTrain ("train_2", 100.0f, intersections[1]);
        train2.setSpeed (0.0f);
        train2.setColor (0, 255, 0);
        Simulation.getScene ().addObject (train2);

        RailwayTrain train3 = new RailwayTrain ("train_3", 100.0f, intersections[2]);
        train3.setSpeed (0.0f);
        train3.setColor (0, 255, 0);
        Simulation.getScene ().addObject (train3);

        RailwayTrain train4 = new RailwayTrain ("train_4", 100.0f, intersections[3]);
        train4.setSpeed (0.0f);
        train4.setColor (0, 255, 0);
        Simulation.getScene ().addObject (train4);

        RailwayTrain train5 = new RailwayTrain ("train_5", 100.0f, intersections[4]);
        train5.setSpeed (0.0f);
        train5.setColor (0, 255, 0);
        Simulation.getScene ().addObject (train5);

        RailwayTrain train6 = new RailwayTrain ("train_6", 100.0f, intersections[15]);
        train6.setSpeed (0.0f);
        train6.setColor (0, 255, 0);
        Simulation.getScene ().addObject (train6);

        RailwayTrain train7 = new RailwayTrain ("train_7", 100.0f, intersections[16]);
        train7.setSpeed (0.0f);
        train7.setColor (0, 255, 0);
        Simulation.getScene ().addObject (train7);

        RailwayTrain train8 = new RailwayTrain ("train_8", 100.0f, intersections[17]);
        train8.setSpeed (0.0f);
        train8.setColor (0, 255, 0);
        Simulation.getScene ().addObject (train8);

        RailwayTrain train9 = new RailwayTrain ("train_9", 100.0f, intersections[18]);
        train9.setSpeed (0.0f);
        train9.setColor (0, 255, 0);
        Simulation.getScene ().addObject (train9);

        RailwayTrain train10 = new RailwayTrain ("train_10", 100.0f, intersections[19]);
        train10.setSpeed (0.0f);
        train10.setColor (0, 255, 0);
        Simulation.getScene ().addObject (train10);
    }
}
