package org.whatpull.mime.gui.view;

import org.whatpull.mime.gui.event.MoveFrameEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * 사용자 화면 유틸
 * Created by yeonsu on 2017-02-14.
 */
public class MainView {

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
        frame.setIconImage(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);
        frame.add(pull());
        frame.setVisible(true);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(49, 98, 199)));
    }

    // PULL SCREEN
    private static JPanel pull() {
        JPanel rowPanel = new JPanel();
        Color color = new Color(67, 116, 217);
        rowPanel.setBackground(Color.WHITE);
        rowPanel.setLayout(new BorderLayout());

        // 01.TOP
        JPanel top = top(rowPanel);
        rowPanel.add(top, BorderLayout.NORTH);
        MoveFrameEvent moveFrameEvent = new MoveFrameEvent(top);
        top.addMouseListener(moveFrameEvent);
        top.addMouseMotionListener(moveFrameEvent);

        // 02.TAB
//        rowPanel.add(tab(), BorderLayout.CENTER);
        return rowPanel;
    }

    // TAB SETTING
    private static JTabbedPane tab() {
        JTabbedPane tabbedPane = new JTabbedPane();

        JComponent tap1 = makeTextPanel("Panel #1");
        tabbedPane.addTab("Tab 1", null, tap1,"Does nothing");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        return tabbedPane;
    }

    // TAB Component SETTING
    protected static JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    // TOP SETTING
    private static JPanel top(JPanel parent) {
        int width = (int) parent.getSize().getWidth();
        JPanel topPanel = new JPanel();
        Color color = new Color(49, 98, 199);
        topPanel.setBackground(color);
        topPanel.setLayout(new GridBagLayout());
        Dimension dimension = new Dimension();
        dimension.setSize(width, 30);
        topPanel.setPreferredSize(dimension);
        topPanel.setBorder(new EmptyBorder(5,15,5,15));

        // 확인필요
        GridBagConstraints constraints = new GridBagConstraints();

        // 프로그램 이름
        JLabel title = new JLabel("MIME", JLabel.LEFT);
        title.setFont(new Font(title.getName(), Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.1;
        topPanel.add(title, constraints);

        // 종료버튼
        JLabel close = new JLabel("X", JLabel.RIGHT);
        close.setFont(new Font(title.getName(), Font.BOLD, 14));
        close.setForeground(Color.WHITE);
        close.setMaximumSize(new Dimension(30,30));
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        constraints.weightx = 0.0;
        topPanel.add(close);

        return topPanel;
    }

}
