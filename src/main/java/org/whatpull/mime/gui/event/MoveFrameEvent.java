package org.whatpull.mime.gui.event;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

/**
 * 전체 프레임 이동 이벤트
 * Created by yeonsu on 2017-02-17.
 */
public class MoveFrameEvent implements MouseListener, MouseMotionListener {

    JComponent target;
    Point start_drag;
    Point start_loc;
    Cursor cursor;

    public MoveFrameEvent(JComponent target) {
        this.target = target;
    }

    public static JFrame getFrame(Container target) {
        if (target instanceof JFrame) {
            return (JFrame) target;
        }
        return getFrame(target.getParent());
    }

    Point getScreenLocation(MouseEvent e) {
        Point cursor = e.getPoint();
        Point target_location = this.target.getLocationOnScreen();
        return new Point((int) (target_location.getX() + cursor.getX()),
                (int) (target_location.getY() + cursor.getY()));
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        target.setCursor(cursor.getDefaultCursor());
        this.start_drag = this.getScreenLocation(e);
        this.start_loc = this.getFrame(this.target).getLocation();
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
        target.setCursor(cursor.getDefaultCursor());
    }

    public void mouseDragged(MouseEvent e) {
        target.setCursor(cursor.getPredefinedCursor(cursor.MOVE_CURSOR));
        Point current = this.getScreenLocation(e);
        Point offset = new Point((int) current.getX() - (int) start_drag.getX(), (int) current.getY() - (int) start_drag.getY());
        JFrame frame = this.getFrame(target);
        Point new_location = new Point((int) (this.start_loc.getX() + offset.getX()), (int) (this.start_loc.getY() + offset.getY()));
        frame.setLocation(new_location);
    }

    public void mouseMoved(MouseEvent e) {

    }
}
