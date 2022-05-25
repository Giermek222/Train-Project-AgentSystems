package model;

import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import simulation.GraphicsContext;
import simulation.IPositionedObject;
import simulation.IRenderableObject;
import simulation.SimulationObject;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

public class RailwayIntersection extends SimulationObject implements IPositionedObject, IRenderableObject {
    private final static NVGColor COlOR = GraphicsContext.colorFromRgb (255, 0, 0);
    private Vector2f m_position;

    public RailwayIntersection (String name, Vector2f position) {
        super (name);
        m_position = new Vector2f (position.x, position.y);
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
    }
}
