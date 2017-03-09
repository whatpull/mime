package org.whatpull.mime.gui.custom;

import java.awt.*;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * [GUI]커스텀 탭
 * [Project] mime
 * [Package] org.whatpull.mime.gui.custom
 * Created by yeonsu on 2017-03-10
 */
public class TabCustom extends BasicTabbedPaneUI {

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) { }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) { }

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
