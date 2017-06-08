package com.nx.util.jme3.lemur.tween;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.anim.AbstractTween;
import com.simsilica.lemur.anim.Tween;
import com.simsilica.lemur.anim.Tweens;

/**
 * Created by NemesisMate on 2/06/17.
 */
public class PanelTweensMore {


    public static Tween resize(Panel target, Vector3f from, Vector3f to, double length ) {
        from = from != null ? from : target.getLocalScale();
        to = to != null ? to : target.getLocalScale();
        return new PanelSizeTween(target, from, to, length);
    }


    public static Tween remove(Node target, Container parent ) {
        return Tweens.callMethod(parent, "removeChild", target);
    }

    private static final Object[] patch = new Object[0];
    public static Tween add( Panel target, Container parent ) {
        return Tweens.callMethod(parent, "addChild", target, patch);
    }


    /**
     * Created by NemesisMate on 18/01/17.
     *
     * Based on SpatialTweens' ScaleSpatial
     * @see com.simsilica.lemur.anim.SpatialTweens.ScaleSpatial
     */
    private static class PanelSizeTween extends AbstractTween {

        Panel target;
        Vector3f from;
        Vector3f to;

        final Vector3f value;

        public PanelSizeTween(Panel target, Vector3f from, Vector3f to, double length) {
            super(length);

            this.target = target;
            this.from = from.clone();
            this.to = to.clone();
            this.value = from.clone();
        }

        @Override
        protected void doInterpolate(double t) {
            value.interpolateLocal(from, to, (float)t);
            target.setPreferredSize(value);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "[target=" + target + ", from=" + from + ", to=" + to + ", length=" + getLength() + "]";
        }
    }



}
