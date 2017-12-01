package glupak.chat.server.gui;

import javax.swing.*;

import glupak.chat.server.core.ChatServerListener;
import glupak.chat.server.core.SQLSecurityManager;
import glupak.chat.server.core.ServerCore;
import glupak.chat.server.gui.ChatServerConstant;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatServerGUI extends JFrame implements ActionListener, ChatServerListener, Thread.UncaughtExceptionHandler{

    private static final int POS_X = 300;
    private static final int POS_Y = 150;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static final String TITLE = "Chat server";
    private static final String START_LISTENING = "Start listening";
    private static final String STOP_LISTENING = "Stop listening";
    private static final String DROP_ALL_CLIENTS = "Drop all client";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override


            public void run() {
                new ChatServerGUI();
            }
        });
    }
// master


    private final ServerCore serverCore=new ServerCore(this, new SQLSecurityManager());
    private final JButton btnStartListening = new JButton(START_LISTENING);
    private final JButton btnStopListening = new JButton(STOP_LISTENING);
    private final JButton btnDropAllClients = new JButton(DROP_ALL_CLIENTS);
    private final JTextArea log = new JTextArea();

    private ChatServerGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setTitle(TITLE);
        JPanel upperPanel = new JPanel(new GridLayout(1, 3));
        btnDropAllClients.addActionListener(this);
        btnStartListening.addActionListener(this);
        btnStopListening.addActionListener(this);
        upperPanel.add(btnStartListening);
        upperPanel.add(btnDropAllClients);
        upperPanel.add(btnStopListening);
        add(upperPanel, BorderLayout.NORTH);
        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        add(scrollLog, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnStartListening)
        {
            serverCore.startListening(8189);
        } else {
            if (src == btnDropAllClients) {
                serverCore.dropAllClients();
            } else {
                if (src == btnStopListening) {
                    serverCore.stopListening();
                } else {

                    throw new RuntimeException("Unknow src-" + src);
                }
            }
        }
    }

    @Override
    public void onChatServerLog(ServerCore serverCore, String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg+"\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        StackTraceElement[] stackTraceElements=e.getStackTrace();
        String msg;
        if (stackTraceElements.length==0){
            msg="Пустой StackTraceElement";
        } else{
            msg=e.getClass().getCanonicalName()+": "+e.getMessage()+"\n"+stackTraceElements[0];

        }
        JOptionPane.showMessageDialog(null,msg,"Exeption",JOptionPane.ERROR_MESSAGE);
        System.exit(1);

    }
}