package simulation;

import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

public class GraphicsContext {
    private final long m_window;
    private final long m_nvg;
    private final IntBuffer m_bufferWidth;
    private final IntBuffer m_bufferHeight;

    public GraphicsContext (long window) {
        m_window = window;

        // allocate buffers for window width and height
        m_bufferWidth = BufferUtils.createIntBuffer (1);
        m_bufferHeight = BufferUtils.createIntBuffer (1);

        m_nvg = nvgCreate (NVG_ANTIALIAS | NVG_STENCIL_STROKES);
    }

    // private for this package
    void startFrame () {
        glfwGetWindowSize (m_window, m_bufferWidth, m_bufferHeight);
        glViewport (0, 0, m_bufferWidth.get (0), m_bufferHeight.get (0));

        // clear the viewport
        glClearColor (0.4f, 0.5f, 0.75f, 1.0f);
        glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    long nvgBegin () {
        nvgBeginFrame (m_nvg, m_bufferWidth.get (0), m_bufferHeight.get (0), 1);
        return m_nvg;
    }

    void nvgEnd () {
        nvgEndFrame (m_nvg);
    }

    void endFrame () {
        glfwSwapBuffers (m_window);
        glfwPollEvents ();
    }

    void terminate () {
        nvgDelete (m_nvg);
    }
}
