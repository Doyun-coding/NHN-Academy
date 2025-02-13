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

package com.nhnacademy.server.thread.channel;

import org.apache.commons.lang3.StringUtils;

import java.net.Socket;
import java.util.Objects;

public class Session {
    private static final ThreadLocal<Socket> currentSocketLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> currentIdLocal = new ThreadLocal<>();

    public static void initializeSocket(Socket socket){
        currentSocketLocal.set(socket);
    }

    public static void initializeId(String id){
        currentIdLocal.set(id);
    }

    public static void reset(){
        currentIdLocal.remove();
        currentSocketLocal.remove();
    }

    public static Socket getCurrentSocket(){
        return currentSocketLocal.get();
    }

    public static String getCurrentId(){
        return currentIdLocal.get();
    }

    public static boolean isLogin(){
        return Objects.nonNull(currentIdLocal.get());
    }
}
