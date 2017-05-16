package com.nx.util.jme3.lemur.layout;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.VAlignment;
import com.simsilica.lemur.core.GuiControl;

/**
 * Created by NemesisMate on 27/01/17.
 */
public class CenterAlignLayout extends SingleChildLayout {

    HAlignment hAlignment;
    VAlignment vAlignment;

    public CenterAlignLayout() {
        this(HAlignment.Center, VAlignment.Center);
    }

    public CenterAlignLayout(HAlignment hAlignment) {
        this(hAlignment, VAlignment.Center);
    }

    public CenterAlignLayout(VAlignment vAlignment) {
        this(HAlignment.Center, vAlignment);
    }

    public CenterAlignLayout(HAlignment hAlignment, VAlignment vAlignment) {
        this.hAlignment = hAlignment;
        this.vAlignment = vAlignment;
    }

    public HAlignment gethAlignment() {
        return hAlignment;
    }

    public void sethAlignment(HAlignment hAlignment) {
        if(this.hAlignment != hAlignment) {
            this.hAlignment = hAlignment;

            invalidate();
        }
    }

    public VAlignment getvAlignment() {
        return vAlignment;
    }

    public void setvAlignment(VAlignment vAlignment) {
        if(this.vAlignment != vAlignment) {
            this.vAlignment = vAlignment;

            invalidate();
        }
    }

    public void setAlignment(HAlignment hAlignment, VAlignment vAlignment) {
        sethAlignment(hAlignment);
        setvAlignment(vAlignment);
    }

    @Override
    public CenterAlignLayout clone() {
        // Easier and better to just instantiate with the proper
        // settings
        CenterAlignLayout result = new CenterAlignLayout();
        return result;
    }


    @Override
    protected void calculatePreferredSize(Vector3f size, Node child) {

    }

    @Override
    protected void reshape(Vector3f pos, Vector3f size, Node child) {
        if(child != null) {
            Vector3f prefSize = ((Panel)child).getPreferredSize();

            GuiControl guiControl = child.getControl(GuiControl.class);
            Vector3f applySize = guiControl.getSize(); // WARNING!: If the implementation changes, this could fail, if is the case, instantiate here a new vector.

            applySize.x = size.x * prefSize.x / 100f;
            applySize.y = size.y * prefSize.y / 100f;

            guiControl.setSize(applySize);

            float x = 0;
            float y = 0;

            switch (hAlignment) {
                case Center:
                    x = (size.x - applySize.x) / 2f;
                    break;
                case Right:
                    x = size.x - applySize.x;
                    break;
            }

            switch (vAlignment) {
                case Center:
                    y = (size.y - applySize.y) / -2f;
                    break;
                case Bottom:
                    y = -(size.y - applySize.y);
                    break;
            }

            // WARNING!: If the implementation changes, this could fail, if is the case, instantiate here a new vector.
//            child.setLocalTranslation(child.getLocalTranslation().set(pos).addLocal(
//                                                                            (size.x - applySize.x) / 2f,
//                                                                            (size.y - applySize.y) / -2f,
//                                                                            0));
            child.setLocalTranslation(child.getLocalTranslation().set(pos).addLocal(x, y, 0));
        }
    }

}
