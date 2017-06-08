package com.nx.util.jme3.lemur;

import com.nx.util.jme3.lemur.layout.CenterAlignLayout;
import com.nx.util.jme3.lemur.layout.WrapperLayout;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.anim.*;
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

}
