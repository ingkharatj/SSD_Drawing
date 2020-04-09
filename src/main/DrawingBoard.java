package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import objects.*;

public class DrawingBoard extends JPanel {

    private MouseAdapter mouseAdapter;
    private List<GObject> gObjects;
    private GObject target;

    private int gridSize = 10;

    public DrawingBoard() {
        gObjects = new ArrayList<GObject>();
        mouseAdapter = new MAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        setPreferredSize(new Dimension(800, 600));
    }

    public void addGObject(GObject gObject) {
        gObjects.add(gObject);
        repaint();
    }

    public void groupAll() {

        CompositeGObject comp_Object = new CompositeGObject();

        for (GObject gObject : gObjects){
            comp_Object.add(gObject);
        }
        gObjects.clear();
        comp_Object.recalculateRegion();
        gObjects.add(comp_Object);

        repaint();


    }

    public void deleteSelected() {
        gObjects.remove(target);

        repaint();

    }

    public void clear() {

        gObjects.clear();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintBackground(g);
        paintGrids(g);
        paintObjects(g);
    }

    private void paintBackground(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void paintGrids(Graphics g) {
        g.setColor(Color.lightGray);
        int gridCountX = getWidth() / gridSize;
        int gridCountY = getHeight() / gridSize;
        for (int i = 0; i < gridCountX; i++) {
            g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
        }
        for (int i = 0; i < gridCountY; i++) {
            g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
        }
    }

    private void paintObjects(Graphics g) {
        for (GObject go : gObjects) {
            go.paint(g);
        }
    }

    class MAdapter extends MouseAdapter {

        int x = 0;
        int y = 0;

        private void deselectAll() {

            for(GObject gObject : gObjects){
                gObject.deselected();

            }
            target = null;
        }

        @Override
        public void mousePressed(MouseEvent e) {


            this.x = e.getX();
            this.y = e.getY();

            deselectAll();

            for(GObject gObject : gObjects){
                if (gObject.pointerHit(x, y)){
                    target = gObject;
                    target.selected();
                    break;
                }
            }
            repaint();


        }

        @Override
        public void mouseDragged(MouseEvent e) {

            if(target != null){
                target.move(e.getX() - x, e.getY() - y);
                x = e.getX();
                y = e.getY();
            }
            repaint();
        }
    }

}