package model;

import org.javatuples.Pair;
import util.SegmentParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RailwayPlan {
    private Map<String, List<String> > map = new HashMap<>();

    public RailwayPlan(List<String> segments) {
        for (String s : segments) {
            Pair<String, String> parsed = SegmentParser.parse(s);
            if (!map.containsKey(parsed.getValue0()))
                map.put(parsed.getValue0(), new ArrayList<>() {});
            map.get(parsed.getValue0()).add(parsed.getValue1());
        }

    }

}
