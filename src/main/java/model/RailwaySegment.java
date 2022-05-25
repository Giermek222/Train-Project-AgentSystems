package model;

import org.joml.Vector2f;
import simulation.GraphicsContext;
import simulation.IRenderableObject;
import simulation.SimulationObject;

public class RailwaySegment extends SimulationObject implements IRenderableObject {
    private final RailwayIntersection m_startIntersection;
    private final RailwayIntersection m_endIntersection;
    private final float m_length;

    public float getLength () {
        return m_length;
    }

    public RailwaySegment (String name, RailwayIntersection start, RailwayIntersection end) {
        super (name);

        m_startIntersection = start;
        m_endIntersection = end;

        m_length = m_startIntersection.getPosition ().distance (m_endIntersection.getPosition ());
    }

    @Override
    public void glRender (GraphicsContext context) {
    }

    @Override
    public void nvgRender (long nvg) {

    }
}
