package org.whatpull.mime.util;

import javax.swing.*;

/**
 * 사용자 화면 유틸
 * Created by yeonsu on 2017-02-14.
 */
public class GUI {

    public static void init() {
        try {
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) {
            System.out.println("createGUI didn't successfully complete");
        }
    }

    private static void createGUI() {
        JFrame frame = new JFrame("mime");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 768);
        frame.setVisible(true);
    }

}
