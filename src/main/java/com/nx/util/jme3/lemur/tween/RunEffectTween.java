package com.nx.util.jme3.lemur.tween;

import com.simsilica.lemur.Panel;
import com.simsilica.lemur.anim.Animation;
import com.simsilica.lemur.anim.AnimationState;
import com.simsilica.lemur.anim.Tween;
import com.simsilica.lemur.effect.EffectControl;

/**
 * Created by NemesisMate on 23/02/17.
 */
public class RunEffectTween implements Tween {

    final Panel panel;
    final String effectName;

    Animation animation;
    AnimationState animationState;


    public RunEffectTween(Panel panel, String effectName) {
        this.panel = panel;
        this.effectName = effectName;
    }

    @Override
    public double getLength() {
        return Double.MAX_VALUE;
    }

    @Override
    public boolean interpolate(double t) {
        if(animation == null) {
            EffectControl effects = panel.getControl(EffectControl.class);
            if( effects == null ) {
                return true;
            }

            animation = effects.runEffect(effectName).getAnimation();
            animationState = AnimationState.getDefaultInstance();
        }

        if(animationState.isRunning(animation)) {
            return false;
        }

        return true;
    }
}
