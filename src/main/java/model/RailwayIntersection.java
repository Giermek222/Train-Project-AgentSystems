package model;

import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import simulation.GraphicsContext;
import simulation.IPositionedObject;
import simulation.IRenderableObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.lwjgl.nanovg.NanoVG.*;

public class RailwayIntersection extends RailwayFragment implements IPositionedObject, IRenderableObject {

    @Override
    public float getLength () {
        return 10.0f;
    }
    private static final NVGColor COlOR = GraphicsContext.colorFromRgb (255, 0, 0);

    private final Set<RailwaySegment> m_outbound;
    private final Set<RailwaySegment> m_inbound;

    private RailwaySegment m_nextSegment;
    private Vector2f m_position;

    void addOutboundSegment (RailwaySegment segment) {
        m_outbound.add (segment);

        if (m_nextSegment == null) {
            m_nextSegment = segment;
        }
    }

    void addInboundSegment (RailwaySegment segment) {
        m_inbound.add (segment);
    }

    public void setNextSegment (RailwaySegment segment) {
        if (m_outbound.contains (segment)) {
            m_nextSegment = segment;
        }
    }

    public void setNextSegmentByName (String name) {
        for (RailwaySegment segment : m_outbound) {
            if (segment.getName().equals("segment_" + name)) {
                m_nextSegment = segment;
                return;
            }
        }
        System.out.println("ERROR You cannot go in that direction");
    }

    public Iterator<RailwaySegment> outbound () {
        return m_outbound.iterator ();
    }

    public Iterator<RailwaySegment> inbound () {
        return m_inbound.iterator ();
    }

    @Override
    public RailwaySegment getNextFragment () {
        return m_nextSegment;
    }

    public RailwayIntersection (String name, Vector2f position) {
        super (name);
        m_position = new Vector2f (position.x, position.y);

        m_outbound = new HashSet<> ();
        m_inbound = new HashSet<> ();
    }

    @Override
    public void setPosition (Vector2f position) {
        m_position = position;
    }

    @Override
    public Vector2f getPosition () {
        return new Vector2f (m_position.x, m_position.y);
    }

    @Override
    public void glRender (GraphicsContext context) {
    }

    @Override
    public void nvgRender (long nvg) {
        nvgBeginPath (nvg);
        nvgCircle (nvg, m_position.x, m_position.y, 10.0f);
        nvgFillColor (nvg, COlOR);
        nvgFill (nvg);
        nvgClosePath (nvg);

        // render label
        nvgFontSize (nvg, 16.0f);
        nvgFontFace (nvg, "font");
        nvgTextAlign (nvg, NVG_ALIGN_MIDDLE | NVG_ALIGN_BOTTOM);
        nvgText (nvg, m_position.x, m_position.y - 30, String.format ("%s", getName ()));
        nvgText (nvg, m_position.x, m_position.y - 15, String.format ("to: %s", getNextFragment ().getNextFragment ().getName ()));
    }
}
