package edu.hm.cs.fh.cg.models;

import ba.pohl1.hm.edu.vrlibrary.maths.Matrix4x4;
import ba.pohl1.hm.edu.vrlibrary.model.VRComponent;
import ba.pohl1.hm.edu.vrlibrary.physics.animation.AnimationHandler;
import ba.pohl1.hm.edu.vrlibrary.util.Shader;
import edu.hm.cs.fh.cg.shapes.Cone3D;
import edu.hm.cs.fh.cg.shapes.Cylinder3D;
import edu.hm.cs.fh.cg.shapes.NormalsVisualizer;
import edu.hm.cs.fh.cg.shapes.Sphere3D;

/**
 * Created by Fabio Hellmann on 12.05.2016.
 */
public class Pavilion3D extends VRComponent {
    private static final float TESSELATION = 4f;
    private static final double PI_2 = 2.0f * Math.PI;

    public Pavilion3D(Shader shader, float[] lightpos, final float width, final float height, boolean showNormals) {
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
            if (index > sphereCount) {
                factor *= -1;
            }
            index += factor;

            final Cylinder3D cylinder = new Cylinder3D(shader, lightpos);
            cylinder.identity().translateX(x1).translateZ(z1).scale(cylinderWidth, cylinderHeight, cylinderWidth);
            if(!showNormals) {
                cylinder.setPreAnimationHandler(new AnimationHandler() {
                    @Override
                    public void animate(float delta, Matrix4x4 model) {
                        cylinder.rotateY(1f);
                    }
                });
            }
            add(cylinder);
            if (showNormals) {
                final NormalsVisualizer cylinderNormalsVisualizer = new NormalsVisualizer(shader, cylinder, lightpos);
                cylinderNormalsVisualizer.identity().translateX(x1).translateZ(z1);
                add(cylinderNormalsVisualizer);
            }

            final Sphere3D sphere = new Sphere3D(shader, lightpos);
            sphere.identity().translate(x1, y1, z1).scale(sphereSize, sphereSize, sphereSize);
            if(!showNormals) {
                sphere.setPreAnimationHandler(new AnimationHandler() {
                    private float direction = 1;
                    private float scaleDirection = 1;

                    @Override
                    public void animate(float delta, Matrix4x4 model) {
                        if (sphere.getPosition().y < sphereSize / 2) {
                            direction = 1;
                        } else if (sphere.getPosition().y > cylinderHeight - sphereSize / 2) {
                            direction = -1;
                        }
                        sphere.translateY(.1f * direction);

                        if (sphere.getBoundingBox().getHeight() > sphereSize * 1.5f) {
                            scaleDirection = -1;
                        } else if (sphere.getBoundingBox().getHeight() < sphereSize) {
                            scaleDirection = 1;
                        }
                        sphere.scale(1 + .025f * scaleDirection);
                    }
                });
            }
            add(sphere);

            if (showNormals) {
                final NormalsVisualizer sphereNormalsVisualizer = new NormalsVisualizer(shader, sphere, lightpos);
                sphereNormalsVisualizer.identity().translate(x1, y1, z1);
                add(sphereNormalsVisualizer);
            }
        }

        final Cone3D cone3D = new Cone3D(shader, lightpos);
        cone3D.identity().translateY(cylinderHeight + coneHeight / 2).scale(width, coneHeight, width);
        add(cone3D);

        if (showNormals) {
            final NormalsVisualizer coneNormalsVisualizer = new NormalsVisualizer(shader, cone3D, lightpos);
            coneNormalsVisualizer.identity().translateY(cylinderHeight + coneHeight / 2);
            add(coneNormalsVisualizer);
        }

        for (VRComponent child : getChildren()) {
            child.unscaledTranslate(-3, 0, -3);
        }
    }
}
