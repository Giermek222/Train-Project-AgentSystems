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

    public void initialize(int width, int height, String title) throws RuntimeException {
        if (m_initialized) {
            throw new RuntimeException("simulation was already initialized");
        }

        if (!glfwInit()) {
            throw new RuntimeException("failed to initialize glfw");
        }

        // setup the window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GL_TRUE);

        glfwWindowHint(GLFW_SAMPLES, 4);

        m_window = glfwCreateWindow(width, height, title, NULL, NULL);
        m_initialized = true;
    }

    public void run() {
        if (!m_initialized) {
            throw new RuntimeException("simulation not initialized");
        }

        // setup context in this thread
        glfwMakeContextCurrent(m_window);
        glfwSwapInterval(1);
        glfwShowWindow(m_window);

        GL.createCapabilities();

        IntBuffer bufferWidth = BufferUtils.createIntBuffer(1);
        IntBuffer bufferHeight = BufferUtils.createIntBuffer(1);
        long lastTick = System.currentTimeMillis(), tick, elapsed;
        float deltaTime;

        while (!glfwWindowShouldClose(m_window)) {
            // calculate delta time
            tick = System.currentTimeMillis ();
            elapsed = tick - lastTick;
            lastTick = tick;
            deltaTime = elapsed / 1000f;

            glfwGetWindowSize(m_window, bufferWidth, bufferHeight);
            glViewport(0, 0, bufferWidth.get(0), bufferHeight.get(0));

            // clear the viewport
            glClearColor(0.4f, 0.5f, 0.75f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

            // rendering code goes here

            glfwSwapBuffers(m_window);
            glfwPollEvents();
        }

        terminate();
    }

    private void terminate() {
        glfwDestroyWindow(m_window);
        glfwTerminate();
    }
}
