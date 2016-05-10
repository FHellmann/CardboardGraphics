package cg.edu.hm.pohl.shapes;

import ba.pohl1.hm.edu.vrlibrary.model.VRComponent;
import ba.pohl1.hm.edu.vrlibrary.model.data.GeometryData;
import ba.pohl1.hm.edu.vrlibrary.model.data.GeometryGenerator;
import ba.pohl1.hm.edu.vrlibrary.util.CGUtils;
import ba.pohl1.hm.edu.vrlibrary.util.CardboardGraphics;
import ba.pohl1.hm.edu.vrlibrary.util.Shader;

/**
 * Created by Fabio Hellmann on 10.05.2016.
 */
public class Cube3D extends VRComponent {

    private final GeometryData data;

    public Cube3D() {
        this(CGUtils.randomColor());
    }

    public Cube3D(final float[] color) {
        this(0, 0, 0, color);
    }

    public Cube3D(final float x, final float y, final float z) {
        this(x, y, z, CGUtils.randomColor());
    }

    public Cube3D(final float x, final float y, final float z, final float[] color) {
        setColor(color);
        applyAsColorMaterial(CardboardGraphics.colorShader);
        data = init(x, y, z);
    }

    @Override
    public void applyAsColorMaterial(Shader shader) {
        if(data != null) {
            setGeometryData(data);
        }
        setColor(getColor());
        super.applyAsColorMaterial(shader);
    }

    @Override
    public void applyAsInstancedColorMaterial(Shader shader) {
        if(data != null) {
            setGeometryData(data);
        }
        setColor(getColor());
        super.applyAsInstancedColorMaterial(shader);
    }

    private GeometryData init(float x, float y, float z) {
        final float[] cubeVertices = new float[]{
                // Top
                -0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, -0.5f,
                // Bottom
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,
                // Back
                -0.5f, -0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                // Front
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                // Left
                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                // Right
                0.5f, -0.5f, -0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, 0.5f,
        };
        for(int index = 0; index < cubeVertices.length;) {
            cubeVertices[index++] += x;
            cubeVertices[index++] += y;
            cubeVertices[index++] += z;
        }
        final float[] cubeNormals = new float[]{
                // Top
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                // Bottom
                0, -1, 0,
                0, -1, 0,
                0, -1, 0,
                0, -1, 0,
                0, -1, 0,
                0, -1, 0,
                // Back
                0, 0, -1,
                0, 0, -1,
                0, 0, -1,
                0, 0, -1,
                0, 0, -1,
                0, 0, -1,
                // Front
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                // Left
                -1, 0, 0,
                -1, 0, 0,
                -1, 0, 0,
                -1, 0, 0,
                -1, 0, 0,
                -1, 0, 0,
                // Right
                1, 0, 0,
                1, 0, 0,
                1, 0, 0,
                1, 0, 0,
                1, 0, 0,
                1, 0, 0,
        };
        final float[] cubeTextures = new float[]{
                // Top
                0.f, 1.f,
                1.f, 1.f,
                0.f, 0.f,
                1.f, 1.f,
                1.f, 0.f,
                0.f, 0.f,
                // Bottom
                0.f, 1.f,
                1.f, 1.f,
                0.f, 0.f,
                1.f, 1.f,
                1.f, 0.f,
                0.f, 0.f,
                // Back
                0.f, 1.f,
                1.f, 1.f,
                0.f, 0.f,
                1.f, 1.f,
                1.f, 0.f,
                0.f, 0.f,
                // Front
                0.f, 1.f,
                1.f, 1.f,
                0.f, 0.f,
                1.f, 1.f,
                1.f, 0.f,
                0.f, 0.f,
                // Left
                0.f, 1.f,
                1.f, 1.f,
                0.f, 0.f,
                1.f, 1.f,
                1.f, 0.f,
                0.f, 0.f,
                // Right
                0.f, 1.f,
                1.f, 1.f,
                0.f, 0.f,
                1.f, 1.f,
                1.f, 0.f,
                0.f, 0.f,
        };

        final float[] colorsArray = new float[]{
                // Top
                0.5f, 0.5f, 0.5f, 1.0f,
                -0.5f, 0.5f, 0.5f, 1.0f,
                0.5f, 0.5f, 0.5f, 1.0f,
                -0.5f, 0.5f, -0.5f, 1.0f,
                0.5f, 0.5f, 0.5f, 1.0f,
                0.5f, 0.5f, -0.5f, 1.0f,
                // Bottom
                -0.5f, -0.5f, -0.5f, 1.0f,
                0.5f, -0.5f, 0.5f, 1.0f,
                -0.5f, -0.5f, 0.5f, 1.0f,
                -0.5f, -0.5f, -0.5f, 1.0f,
                0.5f, -0.5f, -0.5f, 1.0f,
                0.5f, -0.5f, 0.5f, 1.0f,
                // Front
                -0.5f, -0.5f, -0.5f, 1.0f,
                -0.5f, 0.5f, -0.5f, 1.0f,
                0.5f, -0.5f, -0.5f, 1.0f,
                0.5f, -0.5f, -0.5f, 1.0f,
                -0.5f, 0.5f, -0.5f, 1.0f,
                0.5f, 0.5f, -0.5f, 1.0f,
                // Back
                -0.5f, -0.5f, 0.5f, 1.0f,
                0.5f, -0.5f, 0.5f, 1.0f,
                -0.5f, 0.5f, 0.5f, 1.0f,
                0.5f, -0.5f, 0.5f, 1.0f,
                0.5f, 0.5f, 0.5f, 1.0f,
                -0.5f, 0.5f, 0.5f, 1.0f,
                // Left
                -0.5f, -0.5f, -0.5f, 1.0f,
                -0.5f, -0.5f, 0.5f, 1.0f,
                -0.5f, 0.5f, 0.5f, 1.0f,
                -0.5f, -0.5f, -0.5f, 1.0f,
                -0.5f, 0.5f, 0.5f, 1.0f,
                -0.5f, 0.5f, -0.5f, 1.0f,
                // Right
                0.5f, -0.5f, -0.5f, 1.0f,
                0.5f, 0.5f, 0.5f, 1.0f,
                0.5f, -0.5f, 0.5f, 1.0f,
                0.5f, -0.5f, -0.5f, 1.0f,
                0.5f, 0.5f, -0.5f, 1.0f,
                0.5f, 0.5f, 0.5f, 1.0f,
        };

        final GeometryData data = new GeometryData(cubeVertices, cubeNormals);
        data.setTexArray(cubeTextures);
        data.setColorsArray(colorsArray);
        return data;
    }
}
