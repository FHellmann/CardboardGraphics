package edu.hm.cs.fh.cg.models;

import ba.pohl1.hm.edu.vrlibrary.maths.Matrix4x4;
import ba.pohl1.hm.edu.vrlibrary.model.VRComponent;
import ba.pohl1.hm.edu.vrlibrary.physics.animation.AnimationHandler;
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
        final int sphereCount = (int) (PI_2 / deltaAngle);
        int index = 1;
        int factor = 2;
        for (float angle = 0.0f; angle < PI_2; angle += deltaAngle) {
            float x1 = (float) (radius * Math.sin(angle));
            float z1 = (float) (radius * Math.cos(angle));
            float y1 = cylinderHeight / sphereCount * index - sphereSize / 2;
            if(index > sphereCount) {
                factor *= -1;
            }
            index += factor;

            final Cylinder3D cylinder = new Cylinder3D(shader);
            cylinder.identity().translateX(x1).translateZ(z1).scale(cylinderWidth, cylinderHeight, cylinderWidth);
            add(cylinder);

            final Sphere sphere = new Sphere();
            sphere.identity().translate(x1, y1, z1).scale(sphereSize, sphereSize, sphereSize);
            sphere.setPreAnimationHandler(new AnimationHandler() {
                private float direction = 1;

                @Override
                public void animate(float delta, Matrix4x4 model) {
                    delta = .1f;
                    if(sphere.getPosition().y < sphereSize / 2) {
                        direction = 1;
                    } else if(sphere.getPosition().y > cylinderHeight - sphereSize / 2) {
                        direction = -1;
                    }
                    sphere.translateY(delta * direction);
                }
            });
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
