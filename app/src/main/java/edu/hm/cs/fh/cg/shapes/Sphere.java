package edu.hm.cs.fh.cg.shapes;

import ba.pohl1.hm.edu.vrlibrary.model.VRComponent;
import ba.pohl1.hm.edu.vrlibrary.model.data.GeometryGenerator;
import ba.pohl1.hm.edu.vrlibrary.util.CGUtils;
import ba.pohl1.hm.edu.vrlibrary.util.CardboardGraphics;
import ba.pohl1.hm.edu.vrlibrary.util.Shader;

/**
 * Created by Fabio Hellmann on 10.05.2016.
 */
public class Sphere extends VRComponent {

    public Sphere() {
        this(CGUtils.randomColor());
    }

    public Sphere(final float[] color) {
        setColor(color);
        applyAsColorMaterial(CardboardGraphics.colorShader);
    }

    @Override
    public void applyAsColorMaterial(Shader shader) {
        setGeometryData(GeometryGenerator.createSphere(false));
        setColor(getColor());
        super.applyAsColorMaterial(shader);
    }

    @Override
    public void applyAsInstancedColorMaterial(Shader shader) {
        setGeometryData(GeometryGenerator.createSphere(true));
        setColor(getColor());
        super.applyAsInstancedColorMaterial(shader);
    }
}
