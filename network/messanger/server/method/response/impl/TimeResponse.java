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
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeResponse implements Response {

    private final static String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String getMethod() {
        return "time";
    }

    @Override
    public String execute(String value) {
        LocalDateTime now = LocalDateTime.now();

        if(StringUtils.isEmpty(value)){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);
            return now.format(dateTimeFormatter);
        }

        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(value);
            return now.format(dateTimeFormatter);
        }catch (Exception e){
            return now.format(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT));
        }
    }
}