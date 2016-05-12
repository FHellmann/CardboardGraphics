package edu.hm.cs.fh.cg.models;

import ba.pohl1.hm.edu.vrlibrary.model.VRComponent;
import ba.pohl1.hm.edu.vrlibrary.util.Shader;
import edu.hm.cs.fh.cg.shapes.Cylinder3D;

/**
 * Created by Fabio Hellmann on 12.05.2016.
 */
public class Pavilion3D extends VRComponent {
    private static final float TESSELATION = 4f;
    private static final double PI_2 = 2.0f * Math.PI;

    public Pavilion3D(Shader shader, final float width, final float height) {
        final float radius = width / 2;
        final double deltaAngle = (Math.PI / TESSELATION);
        for (float angle = 0.0f; angle < PI_2; angle += deltaAngle) {
            float x1 = (float) (radius * Math.sin(angle));
            float z1 = (float) (radius * Math.cos(angle));

            final Cylinder3D cylinder = new Cylinder3D(shader);
            cylinder.translateX(x1).translateZ(z1).scale(1, height, 1);
            add(cylinder);
        }
    }
}
