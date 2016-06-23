package edu.hm.cs.fh.cg.shapes;

import java.nio.FloatBuffer;

import ba.pohl1.hm.edu.vrlibrary.maths.Vector3;

/**
 * Created by Fabio Hellmann on 16.06.2016.
 */
public interface IObject3D {
    FloatBuffer getVertices();

    FloatBuffer getNormals();

    FloatBuffer getColors();

    Vector3 getPosition();

    Vector3 extractScaling();
}
