package model;

import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import simulation.GraphicsContext;
import simulation.IRenderableObject;
import simulation.SimulationObject;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.lwjgl.nanovg.NanoVG.*;

public class RailwayTrain extends SimulationObject implements IRenderableObject {
    private static final NVGColor COlOR = GraphicsContext.colorFromRgb (11, 102, 52);

    private final float m_maxSpeed;
    private float m_speed;
    private NVGColor m_color;

    public Queue<String> intersections = new ArrayDeque<>();

    public Queue<String> segments = new ArrayDeque<>();

    private  boolean isRoadStable = true;

    // location data
    private RailwayFragment m_railwayFragment;
    private float m_location;

    private RailwayIntersection previousIntersection;

    void setLocation (float location) {
        m_location = location;
    }

    void setRailwayFragment (RailwayFragment fragment) {
        m_railwayFragment = fragment;
    }

    public void setColor (int r, int g, int b) {
        m_color = GraphicsContext.colorFromRgb (r, g, b);
    }

    public NVGColor getColor () {
        return m_color;
    }

    public RailwayFragment getRailwayFragment () {
        return m_railwayFragment;
    }

    public float getLocation () {
        return m_location;
    }

    public float getMaxSpeed () {
        return m_maxSpeed;
    }

    public float getSpeed() {return  m_speed;}

    public void setSpeed (float speed) {
        m_speed = Math.min (Math.abs (speed), m_maxSpeed);
    }

    public boolean isRoadStable() {
        return isRoadStable;
    }

    public void setRoadStable(boolean roadStable) {
        isRoadStable = roadStable;
    }

    public RailwayIntersection getPreviousIntersection() {
        return previousIntersection;
    }

    public void setPreviousIntersection(RailwayIntersection previousIntersection) {
        this.previousIntersection = previousIntersection;
    }

    public boolean isTraversingSegment() {
        return m_railwayFragment.getName().contains("segment");
    }

    public RailwayTrain (String name, float maxSpeed, RailwayFragment fragment) {
        super (name);

        m_maxSpeed = Math.abs (maxSpeed);
        m_railwayFragment = fragment;
        m_location = 0.0f;
        m_color = RailwayTrain.COlOR;

        m_railwayFragment.enter (this);
    }

    @Override
    public void update (float deltaTime) {
        float deltaLocation = m_speed * deltaTime;
        m_location = m_location + deltaLocation;

        if (m_location >= m_railwayFragment.getLength ()) {
            m_location = 0.0f;

            // leave and go to the next fragment
            m_railwayFragment.leave (this);
            m_railwayFragment = m_railwayFragment.getNextFragment ();

            if (m_railwayFragment == null) {
                throw new RuntimeException (String.format ("train %s out of bounds", getName ()));
            }

            m_railwayFragment.enter (this);
        }
    }


    @Override
    public void glRender (GraphicsContext context) { }

    @Override
    public void nvgRender (long nvg) {
        Vector2f pos = new Vector2f ();

        if (m_railwayFragment instanceof RailwaySegment segment) {
            Vector2f dir = segment.getDirection ();

            pos = segment.getStartIntersection ().getPosition ();
            pos = pos.add (dir.mul (m_location));
        } else if (m_railwayFragment instanceof RailwayIntersection intersection) {
            pos = intersection.getPosition ();
        }

        nvgBeginPath (nvg);
        nvgCircle (nvg, pos.x, pos.y, 10.0f);
        nvgFillColor (nvg, m_color);
        nvgFill (nvg);
        nvgClosePath (nvg);

        // render label
        nvgFontSize (nvg, 16.0f);
        nvgFontFace (nvg, "font");
        nvgTextAlign (nvg, NVG_ALIGN_MIDDLE | NVG_ALIGN_BOTTOM);
        nvgText (nvg, pos.x, pos.y - 30, String.format ("train: %s", getName ()));
        nvgText (nvg, pos.x, pos.y - 15, String.format ("speed: %.2f", m_speed));
    }
}
