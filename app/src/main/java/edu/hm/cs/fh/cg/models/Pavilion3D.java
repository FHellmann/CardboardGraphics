package edu.hm.cs.fh.cg.models;

import ba.pohl1.hm.edu.vrlibrary.model.VRComponent;
import edu.hm.cs.fh.cg.shapes.Cylinder3D;

/**
 * Created by Fabio Hellmann on 12.05.2016.
 */
public class Pavilion3D extends VRComponent {
    private static final int TESSELLATION = 4;
    private static final double PI_2 = 2.0f * Math.PI;
    private static final double DELTA_ANGLE = (Math.PI / (TESSELLATION / 2));

    public Pavilion3D(final float width, final float height) {
        final float radius = width / 2;
        for (float angle = 0.0f; angle < PI_2; angle += DELTA_ANGLE) {
            float x1 = (float) (radius * Math.sin(angle));
            float z1 = (float) (radius * Math.cos(angle));

            final Cylinder3D cylinder = new Cylinder3D();
            cylinder.translateX(x1).translateZ(z1).scale(1, height, 1);
            add(cylinder);
        }
    }
}
