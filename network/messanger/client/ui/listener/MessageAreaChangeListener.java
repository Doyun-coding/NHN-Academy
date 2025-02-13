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

package com.nhnacademy.client.ui.listener;

import com.nhnacademy.client.ui.form.MessageClientForm;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MessageAreaChangeListener implements DocumentListener {
    private final MessageClientForm messageClientForm;

    public MessageAreaChangeListener(MessageClientForm messageClientForm) {
        this.messageClientForm = messageClientForm;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        handleTextChange();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        handleTextChange();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        handleTextChange();
    }

    private void handleTextChange(){
        //scroll 하단으로 이동
        JScrollBar verticalScrollBar = messageClientForm.getScrollPane().getVerticalScrollBar();
        SwingUtilities.invokeLater(() -> verticalScrollBar.setValue(verticalScrollBar.getMaximum()));
    }

}
