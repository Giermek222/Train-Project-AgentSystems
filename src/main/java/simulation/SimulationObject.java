package simulation;

public abstract class SimulationObject {
    private final String m_name;

    public String getName () {
        return m_name;
    }

    public SimulationObject (String name) {
        m_name = name;
    }

    public void update (float deltaTime) { }
}
