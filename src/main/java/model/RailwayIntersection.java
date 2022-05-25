package model;

import org.joml.Vector2f;
import simulation.GraphicsContext;
import simulation.IPositionedObject;
import simulation.IRenderableObject;
import simulation.SimulationObject;

public class RailwayIntersection extends SimulationObject implements IPositionedObject, IRenderableObject {
    private Vector2f m_position;

    public RailwayIntersection (String name) {
        super (name);
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

    }
}
