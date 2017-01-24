package com.nx.util.jme3.lemur.tween;

import com.jme3.math.Vector3f;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.anim.AbstractTween;

/**
 * Created by NemesisMate on 18/01/17.
 *
 * Based on SpatialTweens' ScaleSpatial
 * @see com.simsilica.lemur.anim.SpatialTweens.ScaleSpatial
 */
public class PanelSizeTween extends AbstractTween {

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
