package org.whatpull.mime.gui.view;

import org.apache.commons.lang3.ObjectUtils;
import org.whatpull.mime.gui.custom.TabCustom;
import org.whatpull.mime.gui.event.MoveFrameEvent;
import org.whatpull.mime.util.AWS;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * [GUI]사용자 화면
 * [Project] mime
 * [Package] org.whatpull.mime.gui.view
 * Created by yeonsu on 2017-03-10
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
        rowPanel.setBackground(new Color(49, 98, 199));
        rowPanel.setLayout(new BorderLayout());

        // 01.TOP
        JPanel top = top(rowPanel);
        rowPanel.add(top, BorderLayout.NORTH);
        MoveFrameEvent moveFrameEvent = new MoveFrameEvent(top);
        top.addMouseListener(moveFrameEvent);
        top.addMouseMotionListener(moveFrameEvent);

        // 02.TAB
        JTabbedPane tap = tab();
        if(ObjectUtils.anyNotNull(tap))
        rowPanel.add(tap, BorderLayout.CENTER);

        return rowPanel;
    }

    // TAB SETTING
    private static JTabbedPane tab() {
        // 아이콘
        try {
            Image img01 = ImageIO.read(MainView.class.getResource("/image/main/icon01.png"));
            img01 = img01.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            Icon tabIcon01 = new ImageIcon(img01);

            Image img02 = ImageIO.read(MainView.class.getResource("/image/main/icon02.png"));
            img02 = img02.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            Icon tabIcon02 = new ImageIcon(img02);

            JTabbedPane tabbedPane = new JTabbedPane() {
                @Override
                public Color getForegroundAt(int index) {
                    if(getSelectedIndex() == index) return Color.WHITE;
                    return new Color(217, 229, 255);
                }
            };

            // 마우스 이벤트
            JComponent tap1 = makePage01();
            tabbedPane.addTab("SETTING", tabIcon01, tap1,"Does nothing");
            tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
            JComponent tab2 = makePage02();
            tabbedPane.addTab("MONITOR", tabIcon02, tab2, "Does nothing");
            tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
            tabbedPane.setUI(new TabCustom());

            return tabbedPane;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // FILE UPLOAD Component SETTING
    protected static JComponent makePage01() {
        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(1, 1));
        panel.setBackground(new Color(217, 229, 255));
        panel.setBorder(BorderFactory.createMatteBorder(2, 0, 5, 0, new Color(49, 98, 199)));
        SettingView settingView = new SettingView();
        panel.add(settingView);
        return panel;
    }

    // INDEX LIST Component SETTING
    protected static JComponent makePage02() {
        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(10, 1));
        panel.setBackground(new Color(217, 229, 255));
        panel.setBorder(BorderFactory.createMatteBorder(2, 0, 5, 0, new Color(49, 98, 199)));
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
        topPanel.setBorder(new EmptyBorder(8,15,8,15));

        // 확인필요
        GridBagConstraints constraints = new GridBagConstraints();

        // 프로그램 이름
        final JLabel title = new JLabel("MIME", JLabel.LEFT);
        title.setFont(new Font(title.getName(), Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.1;
        topPanel.add(title, constraints);

        // 종료버튼
        final JLabel close = new JLabel("X", JLabel.RIGHT);
        close.setFont(new Font(title.getName(), Font.BOLD, 14));
        close.setForeground(Color.WHITE);
        close.setMinimumSize(new Dimension(30,20));
        close.setMaximumSize(new Dimension(30,20));
        close.setPreferredSize(new Dimension(30,20));
        close.addMouseListener(new MouseAdapter() {
            Cursor cursor;

            @Override
            public void mouseEntered(MouseEvent e) {
                close.setForeground(new Color(234, 234, 234));
                close.setFont(new Font(title.getName(), Font.BOLD, 16));
                close.setCursor(cursor.getPredefinedCursor(cursor.HAND_CURSOR));
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                close.setForeground(Color.WHITE);
                close.setFont(new Font(title.getName(), Font.BOLD, 14));
                close.setCursor(cursor.getDefaultCursor());
                super.mouseExited(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                AWS.shutdownDynamoDB();
                System.exit(0);
            }
        });
        constraints.weightx = 0.0;
        topPanel.add(close);

        return topPanel;
    }

}
