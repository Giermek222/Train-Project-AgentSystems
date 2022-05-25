package model;

import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import simulation.GraphicsContext;
import simulation.IRenderableObject;
import simulation.SimulationObject;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

public class RailwaySegment extends SimulationObject implements IRenderableObject {
    private final static NVGColor COlOR = GraphicsContext.colorFromRgb (255, 128, 0);

    private final RailwayIntersection m_startIntersection;
    private final RailwayIntersection m_endIntersection;

    private Vector2f m_startPosition;
    private Vector2f m_endPosition;

    private final float m_length;

    public float getLength () {
        return m_length;
    }

    public RailwaySegment (String name, RailwayIntersection start, RailwayIntersection end) {
        super (name);

        m_startIntersection = start;
        m_endIntersection = end;

        m_startPosition = m_startIntersection.getPosition ();
        m_endPosition = m_endIntersection.getPosition ();

        m_length = m_startIntersection.getPosition ().distance (m_endIntersection.getPosition ());
    }

    @Override
    public void glRender (GraphicsContext context) {
    }

    @Override
    public void nvgRender (long nvg) {
        nvgBeginPath (nvg);
        nvgMoveTo (nvg, m_startPosition.x, m_startPosition.y);
        nvgLineTo (nvg, m_endPosition.x, m_endPosition.y);
        nvgStrokeColor (nvg, COlOR);
        nvgStrokeWidth (nvg, 5.0f);
        nvgStroke (nvg);
        nvgClosePath (nvg);
    }
}
