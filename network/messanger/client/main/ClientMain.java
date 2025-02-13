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

package com.nhnacademy.client.main;

import com.nhnacademy.client.event.subject.MessageSubject;
import com.nhnacademy.client.event.subject.Subject;
import com.nhnacademy.client.runable.MessageClient;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class ClientMain {
    public static void main(String[] args) throws IOException {

        MessageClient messageClient = new MessageClient();
        Thread thread = new Thread(messageClient);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
