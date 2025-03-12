package com.nhnacademy.http.service;

import com.nhnacademy.http.request.HttpRequest;
import com.nhnacademy.http.response.HttpResponse;
import com.nhnacademy.http.util.CounterUtils;
import com.nhnacademy.http.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Objects;

@Slf4j
public class RegisterHttpService implements HttpService {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

        String responseBody = null;

        try {
            responseBody = ResponseUtils.tryGetBodyFromFile(httpRequest.getRequestURI());
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }

        String responseHeader = ResponseUtils.createResponseHeader(200,"UTF-8",responseBody.length());

        try(PrintWriter bufferedWriter = httpResponse.getWriter()){
            bufferedWriter.write(responseHeader);
            bufferedWriter.write(responseBody);
            bufferedWriter.flush();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

        String responseBody = null;

        try {
            responseBody = ResponseUtils.tryGetBodyFromFile(httpRequest.getRequestURI());
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }

        String id = httpRequest.getParameter("userId");
        String userPassword = httpRequest.getParameter("userPassword");
        String userEmail = httpRequest.getParameter("userEmail");

        log.debug("id={}", id);
        log.debug("userPassword={}", userPassword);
        log.debug("userEmail={}", userEmail);

        String responseHeader = ResponseUtils.createRedirectHeader(302, "UTF-8", responseBody.length(), "/index.html" + "?userId="+ id);

        log.debug("responseHeader={}", responseHeader);

        try(PrintWriter bufferedWriter = httpResponse.getWriter()) {
            bufferedWriter.write(responseHeader);
            bufferedWriter.write(responseBody);
            bufferedWriter.flush();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
