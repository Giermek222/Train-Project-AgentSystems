package model;

import simulation.SimulationObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class RailwayFragment extends SimulationObject {
    private final Set<RailwayTrain> m_trains;

    public abstract float getLength ();
    public abstract RailwayFragment getNextFragment ();

    public RailwayFragment (String name) {
        super (name);
        m_trains = new HashSet<> ();
    }

    public int countTrains () {
        return m_trains.size ();
    }

    public Iterator<RailwayTrain> trains () {
        return m_trains.iterator ();
    }

    void enter (RailwayTrain train) {
        m_trains.add (train);
    }

    void leave (RailwayTrain train) {
        if (m_trains.contains (train)) {
            m_trains.remove (train);
        } else {
            throw new RuntimeException (String.format ("train %s was not in %s\n", train.getName (), getName ()));
        }
    }
}
