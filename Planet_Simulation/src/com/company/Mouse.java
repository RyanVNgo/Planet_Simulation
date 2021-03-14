package com.company;

import java.awt.event.*;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    private int mouseX = -1;
    private int mouseY = -1;
    private int mouseB = -1;
    private int mouseScrollValue = 1;

    public int GetMouseX() {
        return this.mouseX;
    }
    public int GetMouseY() {
        return this.mouseY;
    }

    public int GetMouseB() {
        return this.mouseB;
    }

    public int GetMouseScrollValue() {
        return this.mouseScrollValue;
    }


    // - - - - - - - - - -

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.mouseB = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mouseB = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() == -1) {
            mouseScrollValue += 1;
        } else {
            mouseScrollValue -= 1;
        }
    }

}
