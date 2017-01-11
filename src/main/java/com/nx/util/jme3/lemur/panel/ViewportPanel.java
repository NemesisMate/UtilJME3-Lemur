/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nx.util.jme3.lemur.panel;


import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.light.Light;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.event.BasePickState;
import com.simsilica.lemur.event.PickState;
import com.simsilica.lemur.style.ElementId;
import org.slf4j.LoggerFactory;

/**
 *
 * @author xelun
 */
public class ViewportPanel extends Panel {
    protected ViewPort viewport;
    protected Node viewPortNode;
    protected Vector3f lastSize = new Vector3f();
    protected Camera cam;
    protected RenderManager renderManager;
    protected AppStateManager stateManager;

    Vector3f camOrigin = new Vector3f();
//    Vector3f boundsExtents = new Vector3f();
    Vector3f camOffset = new Vector3f();

    protected boolean autoZoom = true;

    private Node rootNode;

    public ViewportPanel(AppStateManager stateManager, ElementId elementid, String style) {
        this(elementid, style);

        setStateManager(stateManager);
    }

    public ViewportPanel(ElementId elementid, String style) {
        super(elementid, style);
        viewPortNode = new Node("Root Node ViewPort Panel");

//        setPreferredSize(new Vector3f(1, 1, 1)); // Patch to the first NaN size value. Try with setSize instead?

        addControl(new AbstractControl() {
            @Override
            protected void controlUpdate(float tpf) {

                if(viewport != null) {

                    Vector3f size = ViewportPanel.this.getSize();
//                    LoggerFactory.getLogger(this.getClass()).debug("Size: {}.", size);
                    if(!size.equals(lastSize)) {
                        lastSize.set(size);
                        setViewPortSize(lastSize);
                    }

                    viewPortNode.updateLogicalState(tpf);

                    if(autoZoom) {
                        Spatial child = viewPortNode.getChild(0);
                        if(child != null) {

                            viewPortNode.updateModelBound();

                            //FIXME: When rotating, the bounds dimension can change, making the y be bigger than the x or z and viceverse, showing an undesired zoom-in-out effect.
                            BoundingBox bb = (BoundingBox) child.getWorldBound();
                            if (bb != null) {
                                float x = bb.getXExtent();
                                float y = bb.getYExtent();
                                float z = bb.getZExtent();


                                float dimensions;

                                float bigger = x;

                                if(z > bigger) {
                                    bigger = z;
                                }

                                if (y > bigger) {
                                    bigger = y;
                                    dimensions = cam.getFrustumTop() - cam.getFrustumBottom();
                                } else {
                                    dimensions = cam.getFrustumRight() - cam.getFrustumLeft();
                                }



                                // Teoria de los triangulos semejantes
                                float distance = (bigger * cam.getFrustumNear()) / (dimensions / 2f);

//                                LoggerFactory.getLogger(this.getClass()).debug("BB center: {}, extents: {}, frustums: [b:{}, t:{}, r:{}, l:{}]distance: {}.",
//                                        bb.getCenter(),
//                                        bb.getExtent(new Vector3f()),
//                                        cam.getFrustumBottom(),
//                                        cam.getFrustumTop(),
//                                        cam.getFrustumRight(),
//                                        cam.getFrustumLeft(),
//                                        distance);



                                //TODO: Set the correct equation in relation with origin to know the perfect cam distance.
                                camOffset.set(bb.getCenter()).addLocal(camOrigin).addLocal(0, 0, distance + bigger);
                                if (!cam.getLocation().equals(camOffset)) {
//                                    LoggerFactory.getLogger(this.getClass()).debug("Setting cam location: {}.", camOffset);
                                    //TODO: Smooth this movement.
                                    cam.setLocation(camOffset);
                                }
                            }
                        }
                    }

                    viewPortNode.updateGeometricState();

                } else {

                    open();

                    Node rootParent = null;
                    Node parent = spatial.getParent();
                    while(parent != null) {
                        rootParent = parent;
                        parent = parent.getParent();
                    }

                    if(rootParent != null) {
                        ViewportPanel.this.rootNode = rootParent;
                        rootParent.addControl(new AbstractControl() {
                            @Override
                            protected void controlUpdate(float tpf) {
                                Node rootParent = null;
                                Node parent = ViewportPanel.this.getParent();
                                while(parent != null) {
                                    rootParent = parent;
                                    parent = parent.getParent();
                                }

                                if(rootParent != spatial) {
                                    close();
                                    spatial.removeControl(this);
                                }
                            }

                            @Override
                            public void setSpatial(Spatial spatial) {


                                if(spatial == null) {
                                    if(viewport != null) {
                                        LoggerFactory.getLogger(this.getClass()).warn("Shouldn't be removing this control manually!");
//                                        this.spatial.addControl(this); // Readd??, or just better let the developer see problem.
                                    }
//                                    close();
                                }

                                super.setSpatial(spatial);
                            }

                            @Override
                            protected void controlRender(RenderManager rm, ViewPort vp) {

                            }
                        });

                    }





//                    open();
                }
//                LoggerFactory.getLogger(this.getClass()).debug("Geom location: {}, scale: {}.", geom.getWorldTranslation(), geom.getWorldScale());
            }

            @Override
            protected void controlRender(RenderManager rm, ViewPort vp) {
//                viewPortNode.updateGeometricState();
            }
        });
    }

    /**
     * If changed to public, other operations with the statemanager should be taken in account.
     * @param stateManager
     */
    private void setStateManager(AppStateManager stateManager) {
        Application app = stateManager.getApplication();

        setCam(app.getCamera().clone());
        setRenderManager(app.getRenderManager());

        this.stateManager = stateManager;
    }

    public void setCam(Camera cam) {
        this.cam = cam.clone();
        this.cam.setFrustumPerspective(40, 1, 0.05f, 500f);
//        this.cam.setLocation(Vector3f.ZERO);
        this.cam.setLocation(new Vector3f(0, 0, 10));
    }
    
    public void setCamPosition(Vector3f position) {
        //If autozoom, this location works as the camera origin
        if(!autoZoom) {
            this.cam.setLocation(position);
        }

        camOrigin.set(position);
    }

    public void setRenderManager(RenderManager renderManager) {
        this.renderManager = renderManager;
    }

    private void open() {
        LoggerFactory.getLogger(this.getClass()).debug("Opening viewport panel");

        setViewPort(renderManager.createPostView("viewportPanel", cam));

        /*
         * Se ha puesto que use la layer "PICK_LAYER_GUI" para que reconozca los eventos de raton, de la otra forma
         * no los reconoce si hay algún panel que tome eventos (aunque no los consuma), tanto debajo como encima de
         * él graficamente.
        */
        stateManager.getState(BasePickState.class).addCollisionRoot(viewPortNode, viewport, PickState.PICK_LAYER_GUI);
    }

    private void close() {
        LoggerFactory.getLogger(this.getClass()).debug("Closing viewport panel");

        clearViewport();
//        renderManager.removePostView(viewport);
//
//        stateManager.getState(BasePickState.class).removeCollisionRoot(viewport);
    }

    protected void clearViewport() {
        if(viewport == null) {
            return;
        }

        renderManager.removePostView(viewport);
        stateManager.getState(BasePickState.class).removeCollisionRoot(viewport);
        lastSize.set(0, 0, 0);

        viewport = null;
    }

    protected void setViewPort(ViewPort viewport) {
        clearViewport();

        this.viewport = viewport;
        if(viewport == null) {
            return;
        }

        this.viewport.setClearFlags(false, true, true);

        // This two lines shouldn't be needed... hm....
        viewPortNode.updateModelBound();
        viewPortNode.updateGeometricState();
        //////////////////////////

        this.viewport.attachScene(viewPortNode);
    }

    protected void setViewPortSize(Vector3f size) {
        if(viewport == null) {
            return;
        }

        LoggerFactory.getLogger(this.getClass()).debug("VIEWPORT SIZE: {}", size);

//        Vector2f aux2f = new Vector2f();
        Vector3f aux3f = Vector3f.UNIT_Z.negate();

        cam.lookAtDirection(aux3f, Vector3f.UNIT_Y);

        Vector3f pos = this.getWorldTranslation();

//        float h = Display.getHeight();
//        float w = Display.getWidth();
        float h = cam.getHeight();
        float w = cam.getWidth();

        float top    = (pos.y - 10 ) / h;
        float bottom = (pos.y - size.y + 10) / h;
        float left   = (pos.x + 10) / w;
        float right  = (pos.x + size.x - 10) / w;
        
        
//        float top    = (pos.y ) / h;
//        float bottom = (pos.y - size.y ) / h;
//        float left   = (pos.x ) / w;
//        float right  = (pos.x + size.x ) / w;

        cam.setViewPort(left, right, bottom, top);
//        cam.setFrustumPerspective(40, size.x/size.y, 0.05f, 500f);
//        viewport.getCamera().setParallelProjection(true);
        updatePerspective(size);
    }

    protected void updatePerspective(Vector3f size) {
        cam.setFrustumPerspective(40, size.x/size.y, 0.05f, 500f);
    }


    public boolean isAutoZoom() {
        return autoZoom;
    }

    public void setAutoZoom(boolean autoZoom) {
        this.autoZoom = autoZoom;
    }

    @Override
    public void addLight(Light light) {
        viewPortNode.addLight(light);
    }

    public void attachScene(Spatial spatial) {
        viewPortNode.detachAllChildren();
        viewPortNode.attachChild(spatial);
        LoggerFactory.getLogger(this.getClass()).debug("Attaching to scene");
    }
}
