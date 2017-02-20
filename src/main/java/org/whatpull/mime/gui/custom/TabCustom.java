package org.whatpull.mime.gui.custom;

import java.awt.*;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * Created by yeonsu on 2017-02-19
 * since 2017-02-19
 */
public class TabCustom extends BasicTabbedPaneUI {

    private Polygon shape;

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        /*Graphics2D g2D = (Graphics2D) g;
        GradientPaint gradientShadow;

        int xp[] = null;
        int yp[] = null;

        switch (tabPlacement) {
            case LEFT:
                xp = new int[] { x, x, x+w, x+w, x};
                yp = new int[] { y, y+h-3, y+h-3, y, y};
                gradientShadow = new GradientPaint(x, y, new Color(0,0,255), x, y+h, Color.ORANGE);
                break;
            case RIGHT:
                xp = new int[] { x, x, x+w-2, x+w-2, x };
                yp = new int[] { y, y+h-3, y+h-3, y, y };
                gradientShadow = new GradientPaint( x, y, new Color(0,0,255), x, y+h, new Color(153,186,243));
                break;
            case BOTTOM:
                xp = new int[] { x, x, x+3, x+w-inclTab-6, x+w-inclTab-2, x+w-inclTab, x+w-3, x };
                yp = new int[] { y, y+h-3, y+h, y+h, y+h-1, y+h-3, y, y };
                gradientShadow = new GradientPaint(x, y, new Color(0,0,255), x, y+h, Color.BLUE);
                break;
            case TOP:
            default:
                xp = new int[] { x, x, x+3, x+w-inclTab-6, x+w-inclTab-2, x+w-inclTab, x+w, x };
                yp = new int[] { y+h, y+3, y, y, y+1, y+3, y+h, y+h};
                gradientShadow = new GradientPaint(0, 0, new Color(255,255,255), 0, y+h/4, new Color(153,186,243));
                break;
        }

        shape = new Polygon(xp, yp, xp.length);

        if(isSelected) {
            g2D.setColor(new Color(192,192,192));
            g2D.setPaint(gradientShadow);
        } else {
            g2D.setColor(tabPane.getBackgroundAt(tabIndex));
            g2D.setColor(new Color(192,192,192));
        }

        g2D.fill(shape);

        if(runCount > 1) {
            g2D.setColor(hazAlfa(getRunForTab(tabPane.getTabCount(), tabIndex)-1));
            g2D.fill(shape);
        }

        g2D.fill(shape);*/
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        if(tabPane.hasFocus() && isSelected) {
            // doing...
        }
    }

    @Override
    protected Insets getContentBorderInsets(int tabPlacement) {
        return new Insets(0,0,0,0);
    }


}
