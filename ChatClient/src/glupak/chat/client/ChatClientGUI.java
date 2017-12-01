package glupak.chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {

    public final int POS_X = 300;
    private static final int POS_Y = 150;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static final String TITLE = "Chat client";


    private final JPanel upperPanel = new JPanel(new GridLayout(2, 3));
    private final JTextField fieldIPAddr = new JTextField("127.0.0.1");
    private final JTextField fieldPort = new JTextField("8189");
    private final JCheckBox chkAlwaysOnTop = new JCheckBox("Always on top", false);
    private final JTextField fieldLogin = new JTextField("Inter login...");
    private final JPasswordField fieldPassword = new JPasswordField();
    private final JButton btnLogin = new JButton("Login");

    private final JTextArea log = new JTextArea();
    private final JList<String> userList = new JList<>();

    private final JPanel bottonPanel = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("Disconnect");
    private final JTextField fieldInput = new JTextField();
    private final JButton btnSend = new JButton("Send");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatClientGUI();
            }
        });
    }

    private ChatClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle(TITLE);
        btnLogin.addActionListener(this);
        btnDisconnect.addActionListener(this);
        btnSend.addActionListener(this);
        chkAlwaysOnTop.addActionListener(this);
        fieldInput.addActionListener(this);
        upperPanel.add(fieldIPAddr);
        upperPanel.add(fieldPort);
        upperPanel.add(chkAlwaysOnTop);
        upperPanel.add(fieldLogin);
        upperPanel.add(fieldPassword);
        upperPanel.add(btnLogin);
        add(upperPanel, BorderLayout.NORTH);

        log.setEnabled(false);
        log.setLineWrap(true);
        JScrollPane scrollLog = new JScrollPane(log);
        add(scrollLog, BorderLayout.CENTER);

        JScrollPane scrollUsers = new JScrollPane(userList);
        scrollUsers.setPreferredSize(new Dimension(150, 0));
        add(scrollUsers, BorderLayout.EAST);

        bottonPanel.add(btnDisconnect, BorderLayout.WEST);
        bottonPanel.add(fieldInput, BorderLayout.CENTER);
        bottonPanel.add(btnSend, BorderLayout.EAST);
        bottonPanel.setVisible(false);
        add(bottonPanel, BorderLayout.SOUTH);
        setVisible(true);

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        String msg;
        if (stackTraceElements.length == 0) {
            msg = "Пустой StackTraceElement";
        } else {
            msg = e.getClass().getCanonicalName() + ": " + e.getMessage() + "\n" + stackTraceElements[0];

        }
        JOptionPane.showMessageDialog(null, msg, "Exeption", JOptionPane.ERROR_MESSAGE);
        System.exit(1);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnSend || e.getSource() == fieldInput) {
            sendMsg();
        }
        if (e.getSource() == btnDisconnect) {
            onDisconnect();
        }
        if (e.getSource() == btnLogin) {
            onConnect();
        }
        if (e.getSource() == chkAlwaysOnTop) {
            setAlwaysOnTop(chkAlwaysOnTop.isSelected());
        }
    }

    public void onConnect() {
        upperPanel.setVisible(false);
        bottonPanel.setVisible(true);
    }

    public void onDisconnect() {
        upperPanel.setVisible(true);
        bottonPanel.setVisible(false);
    }

    public void sendMsg() {
        if (fieldInput.getText().isEmpty()) {
            log.append(fieldInput.getText() + "\n");
            fieldInput.setText("");
            fieldInput.grabFocus();
        }
    }
}

