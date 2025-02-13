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

package com.nhnacademy.client.runable;

import com.nhnacademy.client.event.subject.Subject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;

@Slf4j
public class ReceivedMessageClient implements Runnable {
    private final Socket socket;
    private final Subject subject;

    public ReceivedMessageClient(Socket socket, Subject subject) {
        if(Objects.isNull(socket) || Objects.isNull(subject)){
            throw new IllegalArgumentException();
        }
        this.socket = socket;
        this.subject = subject;
    }

    @Override
    public void run() {
        while (true){
            try(InputStream inputStream = socket.getInputStream();
                BufferedReader clientIn = new BufferedReader(new InputStreamReader(inputStream));
            ){
                String line;
                while((line = clientIn.readLine())!=null){
                    log.debug("recv-message:{}",line);
                    subject.receiveMessage(line);
                }
            } catch (IOException e) {
                log.error("receivedMessage Error:{}",e.getMessage(),e);
                throw new RuntimeException(e);
            }
        }
    }

}
