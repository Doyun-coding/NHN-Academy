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

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.runable.MessageServer;
import com.nhnacademy.server.thread.channel.Session;

import java.util.List;

public class LoginResponse implements Response {
    @Override
    public String getMethod() {
        return "login";
    }

    @Override
    public String execute(String value) {

        if(value.equals("list")){
            List<String> ids = MessageServer.getClientIds();
            return ids.size() >0 ? String.join(System.lineSeparator(), ids) : "empty";
        }

        boolean loginSuccess = MessageServer.addClient(value, Session.getCurrentSocket());

        if(loginSuccess){
            Session.initializeId(value);
            return  "login success!";
        }

        return "login fail";
    }
}
