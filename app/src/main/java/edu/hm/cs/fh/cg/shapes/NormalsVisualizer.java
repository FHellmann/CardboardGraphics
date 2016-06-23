package edu.hm.cs.fh.cg.shapes;

import java.nio.FloatBuffer;

import ba.pohl1.hm.edu.vrlibrary.maths.Matrix4x4;
import ba.pohl1.hm.edu.vrlibrary.maths.Vector3;
import ba.pohl1.hm.edu.vrlibrary.model.VRComponent;
import ba.pohl1.hm.edu.vrlibrary.util.Shader;

/**
 * Created by Fabio Hellmann on 16.06.2016.
 */
public class NormalsVisualizer extends VRComponent {
    public NormalsVisualizer(Shader shader, IObject3D object3D, float[] lightpos) {
        final FloatBuffer vertices = object3D.getVertices();
        final FloatBuffer normals = object3D.getNormals();
        for (int index = 0; index < vertices.capacity(); index += 3) {
            final float locationX = vertices.get(index);
            final float locationY = vertices.get(index + 1);
            final float locationZ = vertices.get(index + 2);
            final float x = normals.get(index);
            final float y = normals.get(index + 1);
            final float z = normals.get(index + 2);

            final Vector3 startPoint = new Vector3(locationX, locationY, locationZ);
            startPoint.mult(object3D.extractScaling());
            final Vector3 endPoint = startPoint.copy();
            endPoint.add(new Vector3(x, y, z));

            add(new Line3D(shader, lightpos, startPoint.getFloat4(), endPoint.getFloat4()));
        }
    }

    @Override
    public Matrix4x4 translate(float x, float y, float z) {
        for (VRComponent component : getChildren()) {
            component.translate(x, y, z);
        }
        return super.translate(x, y, z);
    }
}
