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

import java.util.Objects;

public class WorkerThreadPool {
    private final int poolSize;

    private final static int DEFAULT_POOL_SIZE=5;

    private final Thread[] workerThreads;
    private final Runnable runnable;

    public WorkerThreadPool(Runnable runnable){
        this(DEFAULT_POOL_SIZE, runnable);
    }

    public WorkerThreadPool(int poolSize, Runnable runnable) {

        //poolSize <1 or runnable isNull 다면 IllegalArgumentException이 발생합니다.
        if(poolSize <1 || Objects.isNull(runnable)){
            throw new IllegalArgumentException("poolSize: > 0");
        }

        this.poolSize = poolSize;
        this.runnable = runnable;

        workerThreads = new Thread[poolSize];
        initilizePool();

    }

    private void initilizePool(){
        for(int i=0; i<poolSize; i++){
            workerThreads[i] = new Thread(runnable);
        }
    }

    public synchronized void start(){
        //workerThreads에 초가화된 모든 Thread를 start 합니다.
        for (Thread thread : workerThreads) {
            thread.start();
        }
    }

    public synchronized void stop(){

        for(Thread thread : workerThreads){
            thread.interrupt();
        }

        for(Thread thread : workerThreads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
