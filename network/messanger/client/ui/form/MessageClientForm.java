/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.client.ui.form;

import com.nhnacademy.client.event.action.MessageAction;
import com.nhnacademy.client.event.action.impl.RecvMessageAction;
import com.nhnacademy.client.event.observer.Observer;
import com.nhnacademy.client.event.observer.impl.MessageRecvObserver;
import com.nhnacademy.client.event.subject.EventType;
import com.nhnacademy.client.event.subject.Subject;
import com.nhnacademy.client.ui.listener.InputFieldKeyListener;
import com.nhnacademy.client.ui.listener.MessageAreaChangeListener;
import com.nhnacademy.client.ui.listener.SendButtonEventListener;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class MessageClientForm {
    private JPanel panel;
    private JFrame frame;
    private JTextArea messageArea;
    private JTextField inputField;
    private JButton sendButton;
    private JScrollPane scrollPane;

    private final Subject subject;
    public MessageClientForm(Subject subject) {

        this.subject = subject;

        frame = new JFrame();
        panel = new JPanel();
        messageArea = new JTextArea();
        inputField = new JTextField();
        sendButton = new JButton();
        scrollPane = new JScrollPane(messageArea);

        initializeUi();
        configureEvent();
        configureRecvObserver();
    }

    public JFrame getFrame() {
        return frame;
    }
    public JPanel getPanel() {
        return panel;
    }
    public JTextArea getMessageArea() {
        return messageArea;
    }
    public JTextField getInputField() {
        return inputField;
    }
    public JButton getSendButton() {
        return sendButton;
    }
    public JScrollPane getScrollPane() {
        return scrollPane;
    }
    public Subject getSubject() {
        return subject;
    }

    private void initializeUi(){

        messageArea.setEditable(false);
        //자동 줄바꿈, 가로 스크롤 생김 방지
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setRows(50);
        messageArea.setColumns(100);

        inputField.setColumns(100);

        sendButton.setText("Send");

        panel.setLayout(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.setTitle("Message Client");
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 화면 크기 가져오기
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // 프레임 크기 가져오기
        Dimension frameSize = frame.getSize();
        // 프레임을 화면 가운데로 이동
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }

    private void configureEvent(){
        //sendButton : event
        sendButton.addActionListener(new SendButtonEventListener(this));
        //inputField : key event
        inputField.addKeyListener(new InputFieldKeyListener(this));
        //messageArea : text change event
        messageArea.getDocument().addDocumentListener(new MessageAreaChangeListener(this));
    }

    private void configureRecvObserver(){
        MessageAction messageAction = new RecvMessageAction(this);
        Observer observer = new MessageRecvObserver(messageAction);
        subject.register(EventType.RECV, observer);
    }

    public static void showUI(Subject subject){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MessageClientForm(subject);
            }
        });
    }
}
