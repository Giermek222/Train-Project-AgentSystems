package simulation;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Simulation {
    private long m_window;
    private boolean m_initialized;

    public void initialize (int width, int height, String title) throws RuntimeException {
        if (m_initialized) {
            throw new RuntimeException ("simulation was already initialized");
        }

        if (!glfwInit ()) {
            throw new RuntimeException ("failed to initialize glfw");
        }

        // setup the window
        glfwDefaultWindowHints ();
        glfwWindowHint (GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint (GLFW_VISIBLE, GL_TRUE);

        glfwWindowHint (GLFW_SAMPLES, 4);

        m_window = glfwCreateWindow (width, height, title, NULL, NULL);
        m_initialized = true;
    }

    public void run () {
        if (!m_initialized) {
            throw new RuntimeException ("simulation not initialized");
        }

        // setup context in this thread
        glfwMakeContextCurrent (m_window);
        glfwSwapInterval (1);
        glfwShowWindow (m_window);

        GL.createCapabilities ();

        long lastTick = System.currentTimeMillis (), tick, elapsed;
        float deltaTime;

        GraphicsContext context = new GraphicsContext (m_window);
        while (!glfwWindowShouldClose (m_window)) {
            // calculate delta time
            tick = System.currentTimeMillis ();
            elapsed = tick - lastTick;
            lastTick = tick;
            deltaTime = elapsed / 1000f;

            context.startFrame ();

            // rendering code goes here

            long nvg = context.nvgBegin ();
            context.nvgEnd ();

            // end frame
            context.endFrame ();
        }

        context.terminate ();
        terminate ();
    }

    private void terminate () {
        glfwDestroyWindow (m_window);
        glfwTerminate ();
    }
}
