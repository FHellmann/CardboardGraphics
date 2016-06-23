package edu.hm.cs.fh.cg.shapes;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;

import ba.pohl1.hm.edu.vrlibrary.model.VRComponent;
import ba.pohl1.hm.edu.vrlibrary.rendering.RendererManager;
import ba.pohl1.hm.edu.vrlibrary.util.CGUtils;
import ba.pohl1.hm.edu.vrlibrary.util.Shader;
import edu.hm.cs.fh.cg.DataStructures;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glLineWidth;
import static android.opengl.GLES20.glUniform4fv;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by Fabio Hellmann on 16.06.2016.
 */
public class Line3D extends VRComponent {
    private static final String TAG = Line3D.class.getSimpleName();

    private static final int FLOATS_PER_VERTEX = 3;
    private static final int FLOATS_PER_NORMAL = 3;
    private static final int FLOATS_PER_COLOR = 4;
    private static final int NUMBER_OF_VERTICES = 2 * 3;

    private FloatBuffer verticesBuffer;
    private FloatBuffer colorsBuffer;
    private FloatBuffer normalsBuffer;
    private Shader shader;

    private final float[] start;
    private final float[] end;

    private DataStructures.Matrices matrices = new DataStructures.Matrices();
    private DataStructures.Locations locations = new DataStructures.Locations();

    private float[] lightpos;
    private float[] lightpos_eye = new float[4];

    private DataStructures.LightParameters light = new DataStructures.LightParameters();

    public Line3D(Shader shader, float[] lightpos, float[] start, float[] end) {
        this.shader = shader;
        this.lightpos = lightpos;
        this.start = start;
        this.end = end;

        createLine();

        // Get the shader's attribute and uniform handles used to delegate data from
        // the CPU to the GPU
        locations.vertex_in = this.shader.getAttribute("vertex_in");
        locations.color_in = this.shader.getAttribute("color_in");
        locations.normal_in = this.shader.getAttribute("normal_in");
        locations.pvm = this.shader.getUniform("pvm");
        locations.vm = this.shader.getUniform("vm");
        locations.lightpos = this.shader.getUniform("lightpos");
        locations.light_ambient = this.shader.getUniform("light.ambient");
        locations.light_diffuse = this.shader.getUniform("light.diffuse");
        locations.light_specular = this.shader.getUniform("light.specular");
        // Add this VRComponent in order to be rendered
        RendererManager.getInstance().add(this);
    }

    public void draw(final float[] view, float[] projection) {
        // Use the shader
        shader.use();

        // Set the identity of the matrix
        //identity();

        // Update collision box bounds
        updateBounds(this);

        // Calculate light position in the eye space
        Matrix.multiplyMV(lightpos_eye, 0, view, 0, lightpos, 0);

        // Calculate Model-View and Model-View-Projection matrices
        Matrix.multiplyMM(matrices.vm, 0, view, 0, getFloat16(), 0);
        Matrix.multiplyMM(matrices.pvm, 0, projection, 0, matrices.vm, 0);

        // Put the parameters into the shaders
        glUniformMatrix4fv(locations.pvm, 1, false, matrices.pvm, 0);
        glUniformMatrix4fv(locations.vm, 1, false, matrices.vm, 0);
        glUniform4fv(locations.lightpos, 1, lightpos_eye, 0);
        glUniform4fv(locations.light_ambient, 1, light.ambient, 0);
        glUniform4fv(locations.light_diffuse, 1, light.diffuse, 0);
        glUniform4fv(locations.light_specular, 1, light.specular, 0);

        // Finally draw the cone
        drawLine();

        // Check for possible errors.
        // If there is one, it is most probably related to an issue with the
        // shader params. Copy this line under every 'glUniform' method call
        // to identify the source of the error.
        CGUtils.checkGLError(TAG, "Error while drawing!");
    }

    private void drawLine() {
        glVertexAttribPointer(locations.vertex_in, FLOATS_PER_VERTEX, GL_FLOAT, false, 0, verticesBuffer);
        glVertexAttribPointer(locations.color_in, FLOATS_PER_COLOR, GL_FLOAT, false, 0, colorsBuffer);
        glVertexAttribPointer(locations.normal_in, FLOATS_PER_NORMAL, GL_FLOAT, false, 0, normalsBuffer);
        glLineWidth(5.f);

        glDrawArrays(GLES20.GL_LINES, 0, NUMBER_OF_VERTICES / FLOATS_PER_VERTEX);
    }

    private void createLine() {
        final float[] lineVertices = new float[NUMBER_OF_VERTICES];
        final float[] lineColors = new float[NUMBER_OF_VERTICES / 3 * 4];
        final float[] lineNormals = new float[NUMBER_OF_VERTICES];

        lineVertices[0] = start[0];
        lineVertices[1] = start[1];
        lineVertices[2] = start[2];
        lineVertices[3] = end[0];
        lineVertices[4] = end[1];
        lineVertices[5] = end[2];

        lineColors[0] = 1.f;
        lineColors[1] = 1.f;
        lineColors[2] = 1.f;
        lineColors[3] = 1.f;
        lineColors[4] = 1.f;
        lineColors[5] = 1.f;
        lineColors[6] = 1.f;
        lineColors[7] = 1.f;

        lineNormals[0] = 0f;
        lineNormals[1] = 0f;
        lineNormals[2] = 0f;
        lineNormals[3] = 0f;
        lineNormals[4] = 0f;
        lineNormals[5] = 0f;

        verticesBuffer = CGUtils.createFloatBuffer(lineVertices);
        colorsBuffer = CGUtils.createFloatBuffer(lineColors);
        normalsBuffer = CGUtils.createFloatBuffer(lineNormals);
    }
}
