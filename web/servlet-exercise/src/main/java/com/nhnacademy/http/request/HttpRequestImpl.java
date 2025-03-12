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

package com.nhnacademy.http.request;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class HttpRequestImpl implements HttpRequest {

    private final Socket client;

    private final Map<String,Object> headerMap = new HashMap<>();
    private final Map<String,Object> attributeMap = new HashMap<>();
    private final static String KEY_HTTP_METHOD = "HTTP-METHOD";
    private final static String KEY_QUERY_PARAM_MAP = "HTTP-QUERY-PARAM-MAP";
    private final static String KEY_REQUEST_PATH="HTTP-REQUEST-PATH";
    private final static String HEADER_DELIMER=":";

    public HttpRequestImpl(Socket socket) {
        this.client = socket;
        initialize();
    }

    private void initialize() {

        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            while (true) {
                String line = bufferedReader.readLine();
                //InputStream  i= client.getInputStream();
                // \r\n
                log.debug("line:{}", line);

                if (isFirstLine(line)) {
                    parseHttpRequestInfo(line);
                }else if (isEndLine(line)){
                    log.debug("isEndLine\n");
                    if(getMethod().equals("POST")) {

                        log.debug("headerMap : {}", headerMap.get("Content-Length"));
                        char[] responseBody = new char[Integer.parseInt((String)headerMap.get("Content-Length"))];
                        bufferedReader.read(responseBody);

                        getResponseBody(responseBody);
                    }
                    break;
                }else{
                    parseHeader(line);
                }

                if(line.contains("Content-Length")) {
                    log.debug("CL:{}", line);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    private void getResponseBody(char[] responseBody) {

        String key = "";
        String value = "";

        int idx = 0;
        for(int i = 0; i < responseBody.length; i++) {
            if(responseBody[i] == '=') {
                for(int j = idx; j < i; j++) {
                    key += responseBody[j];
                }
                idx = i+1;
                log.debug("key={}", key);
            }

            if(responseBody[i] == '&') {
                for(int j = idx; j < i; j++) {
                    value += responseBody[j];
                }
                idx = i+1;
                //String v = URLDecoder.decode(value, StandardCharsets.UTF_8);
                String decodedValue;
                try {
                    decodedValue = URLDecoder.decode(value, StandardCharsets.UTF_8.name());
                } catch (Exception e) {
                    decodedValue = value;
                }
                log.debug("value={}", decodedValue);
                getParameterMap().put(key, decodedValue);
                key = "";
                value = "";
            }

            if(i == responseBody.length-1) {
                for(int j = idx; j < i; j++) {
                    value += responseBody[j];
                }
                //String v = URLDecoder.decode(value, StandardCharsets.UTF_8);
                String decodedValue;
                try {
                    decodedValue = URLDecoder.decode(value, StandardCharsets.UTF_8.name());
                } catch (Exception e) {
                    decodedValue = value;
                }
                getParameterMap().put(key, decodedValue);
                break;
            }
        }

    }

    @Override
    public String getMethod() {
        return String.valueOf(headerMap.get(KEY_HTTP_METHOD));
    }
    @Override
    public String getParameter(String name) {
        return String.valueOf(getParameterMap().get(name));
    }

    @Override
    public Map<String, String> getParameterMap() {
        return (Map<String, String>) headerMap.get(KEY_QUERY_PARAM_MAP);
    }

    @Override
    public String getHeader(String name) {
        return String.valueOf(headerMap.get(name));
    }
    @Override
    public void setAttribute(String name, Object o) {
        attributeMap.put(name,o);
    }
    @Override
    public Object getAttribute(String name) {
        return attributeMap.get(name);
    }
    @Override
    public String getRequestURI() {
        return String.valueOf(headerMap.get(KEY_REQUEST_PATH));
    }

    private boolean isFirstLine(String line){
        if(Objects.isNull(line)){
            return false;
        }
        if( line.toUpperCase().indexOf("GET") > -1 || line.toUpperCase().indexOf("POST") > -1 ){
            return true;
        }
        return false;
    }

    private boolean isEndLine(String s){
        return Objects.isNull(s) || s.equals("") ? true : false;
    }

    private void parseHeader(String s){
        String[] hStr = s.split(HEADER_DELIMER);
        String key = hStr[0].trim();
        String value = hStr[1].trim();

        if(Objects.nonNull(key) && key.length()>0) {
            headerMap.put(key, value);
        }
    }

    private void parseHttpRequestInfo(String s) {
        String arr[] = s.split(" ");
        //http method parse
        if (arr.length > 0) {
            headerMap.put(KEY_HTTP_METHOD, s.split(" ")[0]);
        }
        //query parameter parse
        if (arr.length > 2) {
            Map<String, String> queryMap = new HashMap<>();
            int questionIndex = arr[1].indexOf("?");
            String httpRequestPath;

            if(questionIndex>0){
                httpRequestPath = arr[1].substring(0, questionIndex);
            }else{
                httpRequestPath = arr[1];
            }

            String queryString = arr[1].substring(questionIndex + 1, arr[1].length());

            if (Objects.nonNull(queryString) && !httpRequestPath.equals(queryString) ) {
                String qarr[] = queryString.split("&");
                for (String q : qarr) {
                    String key = q.split("=")[0];
                    String value = q.split("=")[1];
                    log.debug("key:{},value={}", key, value);
                    queryMap.put(key.trim(), value.trim());
                }
            }

            //path 설정
            headerMap.put(KEY_REQUEST_PATH, httpRequestPath);

            //queryMap 설정
            headerMap.put(KEY_QUERY_PARAM_MAP, queryMap);
        }
    }

}
