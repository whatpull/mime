package org.whatpull.mime.gui.custom;

import org.apache.commons.lang3.ObjectUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Created by user on 2017-02-21.
 */
public class FileUploadCustom extends JPanel implements ActionListener {

    static private final String newline = "\n";
    JButton credentialButton, connectionButton;
    JTextArea log;
    JFileChooser fileChooser;

    public FileUploadCustom() {
        super(new BorderLayout());

        log = new JTextArea(5, 20);
        log.setPreferredSize(new Dimension());
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setBackground(Color.BLACK);
        log.setForeground(Color.WHITE);
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
        logScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Create a file chooser
        fileChooser = new JFileChooser();

        credentialButton = new JButton("CREDENTIAL", createImageIcon("/image/icon/icon06.png"));
        credentialButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        credentialButton.setHorizontalTextPosition(SwingConstants.CENTER);
        credentialButton.setBackground(Color.WHITE);
        credentialButton.setForeground(Color.BLACK);
        credentialButton.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        credentialButton.setPreferredSize(new Dimension(90, 80));
        credentialButton.addActionListener(this);
        credentialButton.setFocusPainted(false);

        connectionButton = new JButton("CONNECTING", createImageIcon("/image/icon/icon05.png"));
        connectionButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        connectionButton.setHorizontalTextPosition(SwingConstants.CENTER);
        connectionButton.setBackground(Color.WHITE);
        connectionButton.setForeground(Color.BLACK);
        connectionButton.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        connectionButton.setPreferredSize(new Dimension(90, 80));
        connectionButton.addActionListener(this);
        connectionButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel(new GridLayout(10, 1, 0, 5));
        buttonPanel.setBorder(new LineBorder(new Color(49, 98, 199), 5));
        buttonPanel.setBackground(new Color(49, 98, 199));
//        buttonPanel.setBorder(new LineBorder(Color.WHITE));
        buttonPanel.add(credentialButton);
        buttonPanel.add(connectionButton);

        add(buttonPanel, BorderLayout.WEST);
        add(logScrollPane, BorderLayout.CENTER);
    }

    /**
     * 이벤트 액션 행동
     * @param e 이벤트
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == credentialButton) {
            int returnVal = fileChooser.showOpenDialog(FileUploadCustom.this);

            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                // TODO 선택파일 자동으로 저장(OS에 따라 경로 조정)
                log.append("Opening : " + file.getName() + "." + newline);

            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        } else if(e.getSource() == connectionButton) {

        }
    }

    /**
     * 이미지 아이콘 생성
     * @param path 경로
     * @return ImageIcon 객체
     */
    protected  static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = FileUploadCustom.class.getResource(path);
        try {
            Image img = ImageIO.read(imgURL);
            img = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            if(ObjectUtils.allNotNull(img)) {
                return new ImageIcon(img);
            } else {
                System.err.println("Could't find file : " + path);
                return null;
            }
        } catch (IOException e) {
            System.err.println("Could't resize file : " + path);
            return null;
        }
    }
}
