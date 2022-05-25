import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import model.RailwayIntersection;
import org.joml.Vector2f;
import simulation.Simulation;
import simulation.SimulationScene;

public class Trains {
    // this is the main jade entry point function
    // it can be used to set up the platform, there is no need to use any additional agents for setup
    private static void jadeThread () {
        Runtime runtime = Runtime.instance ();
        Profile profileMain = new ProfileImpl (null, 8888, null);

        AgentContainer mc = runtime.createMainContainer (profileMain);

        try {
            AgentController rma = mc.createNewAgent ("rma", "jade.tools.rma.rma", null);
            rma.start ();
        } catch (StaleProxyException exception) {
            exception.printStackTrace ();
        }
    }

    private static void loadSimulation () {
        Simulation.restartScene ();
        SimulationScene scene = Simulation.getScene ();

        scene.addObject (new RailwayIntersection ("intersection_1", new Vector2f (100, 100)));
        scene.addObject (new RailwayIntersection ("intersection_2", new Vector2f (200, 100)));
        scene.addObject (new RailwayIntersection ("intersection_3", new Vector2f (300, 100)));
        scene.addObject (new RailwayIntersection ("intersection_4", new Vector2f (400, 100)));
        scene.addObject (new RailwayIntersection ("intersection_5", new Vector2f (100, 400)));
        scene.addObject (new RailwayIntersection ("intersection_6", new Vector2f (200, 400)));
        scene.addObject (new RailwayIntersection ("intersection_7", new Vector2f (300, 400)));
        scene.addObject (new RailwayIntersection ("intersection_8", new Vector2f (400, 400)));
    }

    public static void main (String[] args) {
        loadSimulation ();

        // create a thread to run jade in
        // we want to keep the main thread for our simulation rendering
        // this is because glfw does not behave well when it is run in non-main thread apparently
        Thread agentPlatformThread = new Thread (Trains::jadeThread);
        //agentPlatformThread.start();

        // now, we want to start the simulation
        Simulation simulation = new Simulation ();
        simulation.initialize (1280, 800, "trains agents project");
        simulation.run ();
    }
}
