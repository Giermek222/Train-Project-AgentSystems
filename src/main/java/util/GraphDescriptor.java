package util;

import model.RailwaySegment;
import org.javatuples.Pair;
import planner.CentralizedPlanner;
import simulation.Simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphDescriptor {
    public static Map<String, List<CentralizedPlanner.RouteDescription>> describeRailway(List<String> segments, List<String> costs) {
        Map<String, List<CentralizedPlanner.RouteDescription>> plan = new HashMap<>();
        int counter = 0;
        for (String s : segments) {

            Pair<String, String> parsed = SegmentParser.parse(s);


            if (!plan.containsKey("intersection_" + parsed.getValue0()))
                plan.put("intersection_" + parsed.getValue0(), new ArrayList<>() {});

            RailwaySegment segment = (RailwaySegment) Simulation.getScene().getObject("segment_" + s);

            CentralizedPlanner.RouteDescription description = new CentralizedPlanner.RouteDescription(
                    "intersection_" + parsed.getValue1(),
                    Float.parseFloat(costs.get(counter)),
                    segment.getLength()
            );
            plan.get("intersection_" + parsed.getValue0()).add(description);
            counter++;
        }
        return plan;
    }
}
