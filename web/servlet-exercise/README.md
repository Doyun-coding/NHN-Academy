회원가입 구현하기

* 아래 구현을 참고하여 RegisterService를 구현하세요.

Branch
* 실습 : execise-simple-http-server-step7 기준으로 execise-simple-http-server-step8 branch를 생성 후 진행 합니다.

구현
GET http://localhost:8080/register.html
* 회원가입 button을 클릭합니다.
* POST http://localhost:8080/register.html로 전송 됩니다.

[source,httprequest]
----
POST /register.html HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded
Content-Length: 64

userId=marco&userPassword=12345&userEemail=marco@nhnacademy.com
----

image:resources/image08.png[]

* register.html
----
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>register</title>
</head>
<body>
    <form method="post" action="./register.html" enctype="application/x-www-form-urlencoded" >
        <table>
            <tr>
                <th>이름</th>
                <td><input type="text" name="userId" value="marco"></td>
            </tr>

            <tr>
                <th>비밀번호</th>
                <td><input type="password" name="userPassword" value="12345"></td>
            </tr>

            <tr>
                <th>이메일</th>
                <td><input type="text" name="userEmail" value="marco@nhnacademy.com"></td>
            </tr>

            <tr>
                <td colspan="2">
                    <button type="submit">회원가입</button>
                </td>
            </tr>

        </table>
    </form>
</body>
</html>
----

=== Post요청 처리
* http://localhost:8080/index.html?userId=marco redirection 될 수 있도록 post 요청을 처리합니다.

[source,httprequest]
----
HTTP/1.1 301 Moved Permanently
Location: http://localhost:8080/index.html?userId=marco
----

* userId는 form으로 전송된 사용자의 id 입니다.

=== index.html에 UserId 출력
* index.html에서는 userId를 다음과 같이 출력합니다.
image:resources/image09.png[]


=== URLEncoding

* URL 인코딩은 URI(Uniform Resource Identifier)를 만들 때 사용되는 특정 문자들을 변환하는 과정입니다.
* 일부 문자가 URL에서 특별한 의미를 가지고 있습니다. 예를 들어, ?, &, = 같은 문자는 URL 쿼리 매개변수와 관련이 있기 때문에 그대로 사용할 수 없습니다.

* 참고
** https://www.urlencoder.org/
** https://developer.mozilla.org/ko/docs/Glossary/Percent-encoding


=== TEST CASE를 통과할 수 있도록 구현 합니다.

[source,java]
----
package com.nhnacademy.http.request;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class HttpRequestImplTest {
    static final long TEST_PORT = 9999;

    static HttpRequest request;

    static Socket client = Mockito.mock(Socket.class);

    final static String CRLF="\r\n";

    @BeforeAll
    static void setUp() throws IOException {

        //id=marco&age=40&name=마르코
        //URLEncoder를 통해서 인코딩 하면 다음과 같이 인코딩 됨니다.
        //id=marco&age=40&name=%EB%A7%88%EB%A5%B4%EC%BD%94

        StringBuilder data = new StringBuilder();
        data.append(String.format("id=%s", URLEncoder.encode("marco",StandardCharsets.UTF_8)));
        data.append(String.format("&age=%s",URLEncoder.encode("40",StandardCharsets.UTF_8)));
        data.append(String.format("&name=%s",URLEncoder.encode("마르코",StandardCharsets.UTF_8)));
        log.debug("data:{}",data.toString());
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("POST /index.html HTTP/1.1%s",CRLF));
        sb.append(String.format("Host: localhost:%d%s",TEST_PORT,CRLF));
        sb.append(String.format("Content-Type: application/x-www-form-urlencoded; charset=UTF-8%s",CRLF));
        sb.append(String.format("Content-Length: %d%s",data.toString().getBytes(StandardCharsets.UTF_8).length,CRLF));
        sb.append(CRLF);
        sb.append(data);

        InputStream inputStream = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        Mockito.when(client.getInputStream()).thenReturn(inputStream);
        request = new HttpRequestImpl(client);
    }

    @Test
    void getMethod() {
        String method = request.getMethod();
        assertEquals("POST",method.toUpperCase());
    }

    @Test
    void getParameterById() {
        String param = request.getParameter("id");
        assertEquals("marco",param);
    }
    @Test
    void getParameterByName() {
        String param = request.getParameter("name");
        assertEquals("마르코",param);
    }

    @Test
    void getParameterByAge() {
        String param = request.getParameter("age");
        assertEquals("40",param);
    }

    @Test
    void getParameterMap() {
        Map<String, Object> expected = new HashMap<>();
        expected.put("id","marco");
        expected.put("age","40");
        expected.put("name","마르코");

        Map actual = request.getParameterMap();
        assertEquals(expected,actual);
    }

    @Test
    void getHeader_contentType() {
        String contentType = request.getHeader("Content-Type");
        log.debug("Content-Type:{}",contentType);
        Assertions.assertTrue(contentType.contains("application/x-www-form-urlencoded"));
    }

    @Test
    void getHeader_charset() {
        String charset = request.getHeader("charset");
        log.debug("charset:{}",charset);
        Assertions.assertTrue(charset.toLowerCase().contains("utf-8"));
    }

    @Test
    void getRequestURI() {
        String uri = request.getRequestURI();
        log.debug("uri:{}", uri);
        Assertions.assertTrue(uri.contains("index.html"));
    }

}
----

== Reference
* https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/301[MDN, Http Redirect]
* https://developer.mozilla.org/ko/docs/Web/HTTP/Methods/POST[MDN, Http Post method]
