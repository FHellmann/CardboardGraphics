package edu.hm.cs.fh.cg;

import ba.pohl1.hm.edu.vrlibrary.model.VRComponent;
import ba.pohl1.hm.edu.vrlibrary.model.shapes.VRRoom;
import ba.pohl1.hm.edu.vrlibrary.navigation.arrow.ArrowTapNavigator;
import ba.pohl1.hm.edu.vrlibrary.ui.AbstractCardboardActivity;
import ba.pohl1.hm.edu.vrlibrary.util.CardboardGraphics;
import ba.pohl1.hm.edu.vrlibrary.util.Shader;
import cg.edu.hm.pohl.R;
import edu.hm.cs.fh.cg.models.Pavilion3D;
import edu.hm.cs.fh.cg.shapes.Cone3D;

/**
 * Created by Pohl on 14.04.2016.
 */
public class CardboardGraphicsActivity extends AbstractCardboardActivity {

    private float[] lightpos = {0.f, 5.f, 0.f, 0.f};

    private Shader pavilionShader;

    @Override
    protected int getCardboardViewId() {
        return R.id.cardboard_view;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.student_scene;
    }

    @Override
    protected float getMoveModifier() {
        return 0.035f;
    }

    @Override
    protected VRComponent createScene() {
        CardboardGraphics.camera.translateY(2f);
        CardboardGraphics.camera.setCanMoveInY(false);
        setNavigator(new ArrowTapNavigator());

        final VRRoom vrRoom = new VRRoom(25, 10, 25);

        final Cone3D cone = new Cone3D(pavilionShader, lightpos);
        cone.translateX(3);
        cone.translateY(2);
        cone.rotateZ(90);
        vrRoom.add(cone);

        final Pavilion3D pavilion = new Pavilion3D(pavilionShader, lightpos, 3, 3, false);
        vrRoom.add(pavilion);

        return vrRoom;
    }

    @Override
    protected void initShaders() {
        super.initShaders();
        pavilionShader = new Shader(R.raw.vertex, R.raw.fragment);
    }

    @Override
    public void onRendererShutdown() {
        super.onRendererShutdown();
        pavilionShader.dispose();
    }
}
