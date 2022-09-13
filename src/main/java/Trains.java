import jade.wrapper.StaleProxyException;
import simulation.Simulation;
import util.ScenarioRunner;
import util.ScenarioEngine;

import java.io.FileNotFoundException;

public class Trains {
    // this is the main jade entry point function
    // it can be used to set up the platform, there is no need to use any additional agents for setup
    private static void jadeThread () {

        try
        {
            ScenarioEngine scenarioEngine = new ScenarioEngine();
            scenarioEngine.runScenario(1);
        }
        catch ( FileNotFoundException | StaleProxyException e)
        {
            throw new RuntimeException(e);
        }


    }

    private static void loadSimulation () {
        Simulation.restartScene ();
        ScenarioRunner.RunScenario1();
    }

    public static void main (String[] args) {
        loadSimulation ();

        // create a thread to run jade in
        // we want to keep the main thread for our simulation rendering
        // this is because glfw does not behave well when it is run in non-main thread apparently

        Thread agentPlatformThread = new Thread (Trains::jadeThread);
        agentPlatformThread.start();

        // now, we want to start the simulation
        Simulation simulation = new Simulation ();
        simulation.initialize (1280, 800, "trains agents project");
        simulation.run ();
    }
}
