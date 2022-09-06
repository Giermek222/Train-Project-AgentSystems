package util;

import org.javatuples.Pair;

public class SegmentParser {
    public static Pair<String, String> parse(String name) {
        String[] parsed = name.split("-");
        return new Pair<>(parsed[0], parsed[1]);
    }
}
