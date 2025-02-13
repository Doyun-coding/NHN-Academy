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

package com.nhnacademy.server.method.response.impl;

import com.nhnacademy.client.event.action.impl.SendMessageAction;
import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.thread.channel.Session;

import java.io.PrintWriter;
import java.net.Socket;

public class WhoamiResponse implements Response {
    private final String METHOD = "whoami";

    @Override
    public String getMethod() {
        //TODO#1-1 method = "whoami" 설정 합니다.
        return METHOD;
    }

    @Override
    public String execute(String value) {
        //TODO#1-2 로그인되어 있지 않다면 "login required!"  반환 합니다.
        if(!Session.isLogin()) {
            return "login required!";
        }

        //TODO#1-3 로그인 되어 있다면 my id is [marco] 형식으로 응답 합니다.
        return "my id is [" + Session.getCurrentId() + "]";
    }
}
