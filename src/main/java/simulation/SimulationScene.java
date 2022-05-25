package simulation;

import java.util.HashMap;
import java.util.Map;

public class SimulationScene {
    private Map<String, SimulationObject> m_objects;

    public SimulationScene () {
        m_objects = new HashMap<> ();
    }

    public void addObject (SimulationObject object) {
        String objectName = object.getName ();
        if (m_objects.containsKey (objectName)) {
            throw new RuntimeException (String.format ("object with name %s already exits", objectName));
        }

        m_objects.put (objectName, object);
    }

    public <T extends SimulationObject> T getObject (String name) {
        return null;
    }
}
