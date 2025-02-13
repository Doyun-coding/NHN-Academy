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

import java.io.PrintWriter;

public class SendMessageAction implements MessageAction {
    public SendMessageAction(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    private final PrintWriter printWriter;

    @Override
    public void execute(String message) {
        printWriter.println(message);
    }
}
