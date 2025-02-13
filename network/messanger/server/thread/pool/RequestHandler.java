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

package com.nhnacademy.server.thread.pool;

import com.nhnacademy.server.thread.channel.Executable;
import com.nhnacademy.server.thread.channel.RequestChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestHandler implements Runnable {
    public RequestHandler(RequestChannel requestChannel) {
        this.requestChannel = requestChannel;
    }

    private final RequestChannel requestChannel;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Executable executable = requestChannel.getJob();
                executable.execute();
            }catch (Exception e){
                if(e instanceof InterruptedException){
                    log.debug("thread 종료!");
                    Thread.currentThread().interrupt();
                }
                log.error("thread-exception : {}", e.getMessage(),e);
            }
        }
    }
}
