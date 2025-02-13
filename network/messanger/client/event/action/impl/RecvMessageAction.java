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

package com.nhnacademy.client.event.action.impl;

import com.nhnacademy.client.event.action.MessageAction;
import com.nhnacademy.client.ui.form.MessageClientForm;

public class RecvMessageAction implements MessageAction {
    private final MessageClientForm messageClientForm;

    public RecvMessageAction(MessageClientForm messageClientForm) {
        this.messageClientForm = messageClientForm;
    }

    @Override
    public void execute(String message) {
        messageClientForm.getMessageArea().append(message);
        messageClientForm.getMessageArea().append(System.lineSeparator());
    }
}
