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

package com.nhnacademy.server.method.response;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class ResponseFactory {
    private static final ArrayList<Response> responseList = new ArrayList<>();
    //maven-repository : https://mvnrepository.com/artifact/org.reflections/reflections
    //[참고] https://www.baeldung.com/reflections-library
    
    static {
        Reflections reflections = new Reflections("com.nhnacademy.server");
        Set<Class<? extends Response>> classes = reflections.getSubTypesOf(Response.class);

        for (Class<? extends Response> clazz : classes) {
            try {
                Response response = clazz.getDeclaredConstructor().newInstance();
                log.debug("response-factory init :  instance :{}", response.getClass().getName() );
                responseList.add(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Response getResponse(String method){
        Response response = responseList.stream()
                .filter(o->o.validate(method))
                .findFirst()
                .orElse(null);

        if(Objects.isNull(response)){
            log.error("response not found : {}",method);
        }
        return response;
    }
}
