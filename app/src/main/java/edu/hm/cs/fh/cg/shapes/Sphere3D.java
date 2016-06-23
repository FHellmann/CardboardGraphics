package edu.hm.cs.fh.cg.shapes;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import ba.pohl1.hm.edu.vrlibrary.model.VRComponent;
import ba.pohl1.hm.edu.vrlibrary.model.data.GeometryData;
import ba.pohl1.hm.edu.vrlibrary.model.data.GeometryGenerator;
import ba.pohl1.hm.edu.vrlibrary.rendering.RendererManager;
import ba.pohl1.hm.edu.vrlibrary.util.CGUtils;
import ba.pohl1.hm.edu.vrlibrary.util.Shader;
import edu.hm.cs.fh.cg.DataStructures;

import static android.opengl.GLES10.glDrawArrays;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glUniform4fv;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by Fabio Hellmann on 10.05.2016.
 */
public class Sphere3D extends VRComponent implements IObject3D {

    private static final String TAG = Sphere3D.class.getSimpleName();

    private static final int FLOATS_PER_VERTEX = 3;
    private static final int FLOATS_PER_NORMAL = 3;
    private static final int FLOATS_PER_COLOR = 4;

    private FloatBuffer verticesBuffer;
    private FloatBuffer colorsBuffer;
    private FloatBuffer normalsBuffer;
    private Shader shader;

    private DataStructures.Matrices matrices = new DataStructures.Matrices();
    private DataStructures.Locations locations = new DataStructures.Locations();
    private DataStructures.AnimationParameters animation = new DataStructures.AnimationParameters();

    private float[] lightpos;
    private float[] lightpos_eye = new float[4];

    private DataStructures.LightParameters light = new DataStructures.LightParameters();
    private ShortBuffer indexBuffer;

    public Sphere3D(Shader shader, float[] lightpos) {
        this.shader = shader;
        this.lightpos = lightpos;

        //light.ambient = new float[] { .23f, .23f, .23f, 1.f };
        //light.diffuse = new float[] { .28f, .28f, .28f, 1.f };
        //light.specular = new float[] { .77f, .77f, .77f, 1.f };

        createSphere();
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
        drawSphere();

        // Check for possible errors.
        // If there is one, it is most probably related to an issue with the
        // shader params. Copy this line under every 'glUniform' method call
        // to identify the source of the error.
        CGUtils.checkGLError(TAG, "Error while drawing!");
    }

    private void drawSphere() {
        glVertexAttribPointer(locations.vertex_in, FLOATS_PER_VERTEX, GL_FLOAT, false, 0, verticesBuffer);
        glVertexAttribPointer(locations.color_in, FLOATS_PER_COLOR, GL_FLOAT, false, 0, colorsBuffer);
        glVertexAttribPointer(locations.normal_in, FLOATS_PER_NORMAL, GL_FLOAT, false, 0, normalsBuffer);

        //glFrontFace(GL_CW);
        if(indexBuffer != null) {
            glDrawElements(GL_TRIANGLES, indexBuffer.capacity(), GL_UNSIGNED_SHORT, indexBuffer);
        } else {
            glDrawArrays(GLES20.GL_TRIANGLES, 0, verticesBuffer.capacity() / FLOATS_PER_VERTEX);
        }
    }

    private void createSphere() {
        final GeometryData sphere = GeometryGenerator.createSphere(false);
        this.verticesBuffer = CGUtils.createFloatBuffer(sphere.getVerticesArray());
        this.normalsBuffer = CGUtils.createFloatBuffer(sphere.getNormalsArray());
        this.colorsBuffer = CGUtils.createFloatBuffer(sphere.getColorsArray());
        this.indexBuffer = CGUtils.createShortBuffer(sphere.getIndicesArray());
    }

    @Override
    public FloatBuffer getVertices() {
        return verticesBuffer;
    }

    @Override
    public FloatBuffer getNormals() {
        return normalsBuffer;
    }

    @Override
    public FloatBuffer getColors() {
        return colorsBuffer;
    }
}
