package edu.hm.cs.fh.cg.models;

import ba.pohl1.hm.edu.vrlibrary.maths.Matrix4x4;
import ba.pohl1.hm.edu.vrlibrary.model.VRComponent;
import ba.pohl1.hm.edu.vrlibrary.util.Shader;
import edu.hm.cs.fh.cg.shapes.Cone3D;
import edu.hm.cs.fh.cg.shapes.Cylinder3D;
import edu.hm.cs.fh.cg.shapes.Sphere;

/**
 * Created by Fabio Hellmann on 12.05.2016.
 */
public class Pavilion3D extends VRComponent {
    private static final float TESSELATION = 4f;
    private static final double PI_2 = 2.0f * Math.PI;

    public Pavilion3D(Shader shader, final float width, final float height) {
        final float cylinderWidth = width / 16;
        final float coneHeight = height / 3;
        final float cylinderHeight = height - coneHeight;
        final float sphereSize = cylinderWidth * 2;

        final float radius = width / 2 - cylinderWidth;
        final double deltaAngle = (Math.PI / TESSELATION);
        for (float angle = 0.0f; angle < PI_2; angle += deltaAngle) {
            float x1 = (float) (radius * Math.sin(angle));
            float z1 = (float) (radius * Math.cos(angle));

            final Cylinder3D cylinder = new Cylinder3D(shader);
            cylinder.identity().translateX(x1).translateZ(z1).scale(cylinderWidth, cylinderHeight, cylinderWidth);
            add(cylinder);

            final Sphere sphere = new Sphere();
            sphere.identity().translateX(x1).translateZ(z1).translateY(sphereSize / 2).scale(sphereSize, sphereSize, sphereSize);
            add(sphere);
        }

        final Cone3D cone3D = new Cone3D(shader);
        cone3D.identity().translateY(cylinderHeight + coneHeight / 2).scale(width, coneHeight, width);
        add(cone3D);

        for (VRComponent child : getChildren()) {
            child.unscaledTranslate(-3, 0, -3);
        }
    }
}
