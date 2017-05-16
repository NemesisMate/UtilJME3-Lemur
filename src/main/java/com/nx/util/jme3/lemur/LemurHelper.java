package com.nx.util.jme3.lemur;

import com.nx.util.jme3.lemur.layout.CenterAlignLayout;
import com.nx.util.jme3.lemur.layout.WrapperLayout;
import com.simsilica.lemur.Container;

/**
 * Created by NemesisMate on 15/05/17.
 */
public final class LemurHelper {

    private LemurHelper() {

    }

    public static Container addAlignedContainer(Container parent) {
        return parent.addChild(new Container(new CenterAlignLayout()));
    }

    public static Container addWrapperContainer(Container parent) {
        return parent.addChild(new Container(new WrapperLayout()));
    }

}
