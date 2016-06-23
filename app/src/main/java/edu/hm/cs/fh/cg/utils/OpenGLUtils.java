package edu.hm.cs.fh.cg.utils;

/**
 * Created by Fabio Hellmann on 09.06.2016.
 */
public class OpenGLUtils {

    public static float[] normalize(float[] _vector){
        float magnitude;
        magnitude = (float)(Math.sqrt(_vector[0]*_vector[0] + _vector[1]*_vector[1]  + _vector[2]*_vector[2]));
        _vector[0] = _vector[0]/magnitude;
        _vector[1] = _vector[1]/magnitude;
        _vector[2] = _vector[2]/magnitude;

        return new float[]{_vector[0], _vector[1], _vector[2]};
    }
}
