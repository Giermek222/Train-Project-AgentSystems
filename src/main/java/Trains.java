import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import model.RailwayIntersection;
import model.RailwaySegment;
import model.RailwayTrain;
import model.json.IntersectionJson;
import model.json.TrainJson;
import org.joml.Vector2f;
import simulation.Simulation;
import simulation.SimulationScene;

import java.io.IOException;
import java.util.List;

public class Trains {
    // this is the main jade entry point function
    // it can be used to set up the platform, there is no need to use any additional agents for setup
    private static void jadeThread() {
        Runtime runtime = Runtime.instance();
        Profile profileMain = new ProfileImpl(null, 8888, null);

        AgentContainer mc = runtime.createMainContainer(profileMain);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<TrainJson> trains = objectMapper.readValue("json-with-trains", new TypeReference<List<TrainJson>>() {});
            List<IntersectionJson> intersections = objectMapper.readValue("json-with-intersections", new TypeReference<List<IntersectionJson>>() {});
            AgentController rma = mc.createNewAgent("rma", "jade.tools.rma.rma", null);
            intersections.forEach(intersectionJson -> {
                try {
                    mc.createNewAgent("rma", "jade.tools.rma.rma", null).start();
                } catch (StaleProxyException e) {
                    throw new RuntimeException(e);
                }
            });
            trains.forEach(trainJson -> {
                try {
                    mc.createNewAgent("rma", "jade.tools.rma.rma", null).start();
                } catch (StaleProxyException e) {
                    throw new RuntimeException(e);
                }
            });
            rma.start();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }


    }

    private static void loadSimulation() {
        Simulation.restartScene();
        SimulationScene scene = Simulation.getScene();

        final RailwayIntersection[] intersections = {
                new RailwayIntersection("intersection_1", new Vector2f(200, 200)),
                new RailwayIntersection("intersection_2", new Vector2f(400, 100)),
                new RailwayIntersection("intersection_3", new Vector2f(600, 250)),
                new RailwayIntersection("intersection_4", new Vector2f(800, 100)),
                new RailwayIntersection("intersection_5", new Vector2f(100, 500)),
                new RailwayIntersection("intersection_6", new Vector2f(400, 300)),
                new RailwayIntersection("intersection_7", new Vector2f(600, 500)),
                new RailwayIntersection("intersection_8", new Vector2f(800, 500)),
                new RailwayIntersection("intersection_9", new Vector2f(250, 600))
        };

        final int[] segments = {12, 23, 34, 16, 36, 37, 48, 51, 56, 67, 78, 79, 83, 95};

        for (int segment : segments) {
            int from = segment / 10, to = segment % 10;
            Simulation.getScene().addObject(new RailwaySegment(
                    String.format("segment_%d-%d", from, to),
                    intersections[from - 1],
                    intersections[to - 1]
            ));
        }

        for (RailwayIntersection intersection : intersections) {
            Simulation.getScene().addObject(intersection);
        }

        RailwayTrain train = new RailwayTrain("train_1", 100.0f, intersections[0]);
        train.setSpeed(100.0f);

        Simulation.getScene().addObject(train);
    }

    public static void main(String[] args) {
        loadSimulation();

        // create a thread to run jade in
        // we want to keep the main thread for our simulation rendering
        // this is because glfw does not behave well when it is run in non-main thread apparently
        Thread agentPlatformThread = new Thread(Trains::jadeThread);
        agentPlatformThread.start();

        // now, we want to start the simulation
        Simulation simulation = new Simulation();
        simulation.initialize(1280, 800, "trains agents project");
        simulation.run();
    }
}
