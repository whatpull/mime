package org.whatpull.mime.gui.view;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.whatpull.mime.util.AWS;
import org.whatpull.mime.util.Confirm;
import org.whatpull.mime.util.CredentialsFile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * 커스텀 파일업로드
 * Created by yeonsu on 2017-02-21.
 */
public class SettingView extends JPanel implements ActionListener {

    static private final String newline = "\n";
    JButton credentialButton, connectionButton;
    JTextArea log;
    JFileChooser fileChooser;

    /**
     * 생성자
     */
    public SettingView() {
        super(new BorderLayout());

        log = new JTextArea();
        log.setPreferredSize(new Dimension());
        log.setLineWrap(true);
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
            int returnVal = fileChooser.showOpenDialog(SettingView.this);

            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                log.append("Opening : " + file.getName() + "." + newline);

                // 확장자 필터(txt / 없는것)
                if(Confirm.getOperatingSystemType() == Confirm.OSType.Windows) {
                    String home = System.getProperty("user.home");
                    StringBuilder sb = new StringBuilder(home);
                    sb.append("\\.aws\\");
                    CredentialsFile.createCredentials(file, sb.toString());
                } else {

                }
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        } else if(e.getSource() == connectionButton) {
            Map<String, Object> result = AWS.configDynamoDB();
            if(ObjectUtils.anyNotNull(result)) {
                try {
                    TableCollection<ListTablesResult> tables = (TableCollection<ListTablesResult>) result.get("tables");
                    if(ObjectUtils.anyNotNull(tables)) {
                        Iterator<Table> iterator = tables.iterator();
                        while (iterator.hasNext()) {
                            Table table = iterator.next();
                            log.append("Table : " + table.getTableName() + " - description[" + table.getDescription() + "]" + newline);
                        }
                    }
                    String msg = (String) result.get("msg");
                    if(StringUtils.isNoneBlank(msg)) {
                        log.append(msg + newline);
                    }
                } catch (Exception exception) {
                    AWS.shutdownDynamoDB();
                    log.append(exception.getMessage() + newline);
                }
            } else {
                log.append("AWS DynamoDB connection fail." + newline);
            }
        }
    }

    /**
     * 이미지 아이콘 생성
     * @param path 경로
     * @return ImageIcon 객체
     */
    protected  static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = SettingView.class.getResource(path);
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
