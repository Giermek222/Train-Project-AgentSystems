package model;

import org.javatuples.Pair;
import util.SegmentParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RailwayPlan {
    private Map<String, List<String> > plan = new HashMap<>();

    public RailwayPlan(List<String> segments) {
        for (String s : segments) {
            Pair<String, String> parsed = SegmentParser.parse(s);
            if (!plan.containsKey(parsed.getValue0()))
                plan.put(parsed.getValue0(), new ArrayList<>() {});
            plan.get(parsed.getValue0()).add(parsed.getValue1());
        }

    }

    public Map<String, List<String>> getPlan() {
        return plan;
    }

    public void setPlan(Map<String, List<String>> plan) {
        this.plan = plan;
    }
}
