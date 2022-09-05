package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RailwayPlan {
    private Map<String, List<String> > map = new HashMap<>();

    public RailwayPlan(List<String> segments) {
        for (String s : segments) {
            String[] segment = s.split("-");
            if (!map.containsKey(segment[0]))
                map.put(segment[0], new ArrayList<>() {});
            map.get(segment[0]).add(segment[1]);
        }

    }

}
