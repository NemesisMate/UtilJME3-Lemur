package com.nx.util.jme3.lemur;

import com.jme3.math.Vector3f;
import com.nx.util.jme3.lemur.layout.CenterAlignLayout;
import com.nx.util.jme3.lemur.layout.WrapperLayout;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Insets3f;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.anim.Animation;
import com.simsilica.lemur.anim.PanelTweens;
import com.simsilica.lemur.anim.TweenAnimation;
import com.simsilica.lemur.anim.Tweens;
import com.simsilica.lemur.core.GuiLayout;
import com.simsilica.lemur.effect.AbstractEffect;
import com.simsilica.lemur.effect.Effect;
import com.simsilica.lemur.effect.EffectInfo;

/**
 * Created by NemesisMate on 15/05/17.
 */
public final class LemurHelper {

    private LemurHelper() {

    }

    /**
     * Warning: normally this isn't the desired effect, but start to fade out from the current alpha value instead of 1.
     */
    public static Effect<Panel> fadeOut = new AbstractEffect<Panel>("fade-In/Out") {
        @Override
        public Animation create(Panel target, EffectInfo existing) {
            return new TweenAnimation(Tweens.smoothStep(PanelTweens.fade(target, 1f, 0f, .2)));
        }
    };

    /**
     * Warning: normally this isn't the desired effect, but start to fade in from the current alpha value instead of 0.
     */
    public static Effect<Panel> fadeIn = new AbstractEffect<Panel>("fade-In/Out") {
        @Override
        public Animation create(Panel target, EffectInfo existing) {
            return new TweenAnimation(Tweens.smoothStep(PanelTweens.fade(target, 0f, 1f, .2)));
        }
    };

    public static Container addAlignedContainer(Container parent) {
        return parent.addChild(new Container(new CenterAlignLayout()));
    }

    public static Container addWrapperContainer(Container parent) {
        return parent.addChild(new Container(new WrapperLayout()));
    }

    public static Container addContainer(Container parent, GuiLayout... layouts) {
        for(GuiLayout layout : layouts) {
            parent = parent.addChild(new Container(layout));
        }

        return parent;
    }


    public static <T extends Panel> T addChild(Container container, T child, float x, float y, float top, float left, float bottom, float right) {
        return addChild(container, child, new Vector3f(x, y, 1), new Insets3f(top, left, bottom, right));
    }

    public static <T extends Panel> T addChild(Container container, T child, float x, float y, float z, float top, float left, float bottom, float right) {
        return addChild(container, child, new Vector3f(x, y, z), new Insets3f(top, left, bottom, right));
    }

    public static <T extends Panel> T addChild(Container container, T child, float x, float y, Insets3f insets) {
        return addChild(container, child, new Vector3f(x, y, 1), insets);
    }

    public static <T extends Panel> T addChild(Container container, T child, float x, float y, float z, Insets3f insets) {
        return addChild(container, child, new Vector3f(x, y, z), insets);
    }

    public static <T extends Panel> T addChild(Container container, T child, float x, float y) {
        return addChild(container, child, new Vector3f(x, y, 1));
    }

    public static <T extends Panel> T addChild(Container container, T child, float x, float y, float z) {
        return addChild(container, child, new Vector3f(x, y, z));
    }

    public static <T extends Panel> T addChild(Container container, T child, float top, float left, float bottom, float right) {
        return addChild(container, child, new Insets3f(top, left, bottom, right));
    }

    public static <T extends Panel> T addChild(Container container, T child, Insets3f insets) {
        container.addChild(child);
        child.setInsets(insets);
        return child;
    }

    public static <T extends Panel> T addChild(Container container, T child, Vector3f prefSize) {
        container.addChild(child);
        child.setPreferredSize(prefSize);
        return child;
    }

    public static <T extends Panel> T addChild(Container container, T child, Vector3f prefSize, Insets3f insets) {
        addChild(container, child, prefSize).setInsets(insets);
        return child;
    }

    public static <T extends Panel> T setProps(T panel, float x, float y, float z, float top, float left, float bottom, float right) {
        return setProps(panel, new Vector3f(x, y, z), new Insets3f(top, left, bottom, right));
    }

    public static <T extends Panel> T setProps(T panel, float x, float y, float top, float left, float bottom, float right) {
        return setProps(panel, new Vector3f(x, y, 1), new Insets3f(top, left, bottom, right));
    }

    public static <T extends Panel> T setProps(T panel, Vector3f prefSize, Insets3f insets) {
        panel.setPreferredSize(prefSize);
        panel.setInsets(insets);
        return panel;
    }

    public static <T extends Panel> T setPrefSize(T panel, float x, float y) {
        return setPrefSize(panel, x, y, 1);
    }

    public static <T extends Panel> T setPrefSize(T panel, float x, float y, float z) {
        panel.setPreferredSize(new Vector3f(x, y, z));
        return panel;
    }

    public static <T extends Panel> T setInsets(T panel, float top, float left, float bottom, float right) {
        panel.setInsets(new Insets3f(top, left, bottom, right));
        return panel;
    }

}
