package edu.hm.cs.fh.cg;

/**
 * Created by Markus Miller on 25.02.2016.
 */
public class DataStructures {

    public static class Locations {
        public int vertex_in;
        public int color_in;
        public int normal_in;

        public int pvm;
        public int vm;

        public int lightpos;

        public int light_diffuse;
        public int light_ambient;
        public int light_specular;
    }

    public static class Matrices {
        public float model[] = new float[16];
        public float view[] = new float[16];
        public float cam[] = new float[16];
        public float projection[] = new float[16];

        public float pvm[] = new float[16];
        public float vm[] = new float[16];
        public float dump[] = new float[16];

        public float[] headRotation = new float[4];
        public float[] headView = new float[16];
    }

    public static class AnimationParameters {
        public float rotationAngle = 0.f;
    }

    public static class LightParameters {
        public float[] ambient = { 0.3f, 0.3f, 0.3f, 0.3f };
        public float[] diffuse = { 1.f, 1.f, 1.f, 1.f };
        public float[] specular = { 0.4f, 0.4f, 0.4f, 0.4f };
    }
}
