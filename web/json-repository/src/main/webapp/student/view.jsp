<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    HttpSession httpSession = request.getSession();
    String id = (String)httpSession.getAttribute("id");
    String name = (String)httpSession.getAttribute("name");
    String gender = (String)httpSession.getAttribute("gender");
    String age = (String)httpSession.getAttribute("age");
    String date = (String)httpSession.getAttribute("date");

%>
<html>
<head>
    <title>학생-조회</title>
    <link rel="stylesheet" href="/style.css" />
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th {
            padding: 8px;
            text-align: center;
        }
        td {
            padding: 8px;
        }
    </style>
</head>
<body>
    <table>
        <tbody>
            <!-- todo view 구현 -->
            <tr>
                <th>아이디</th><td><%=id%></td>
            </tr>
            <tr>
                <th>이름</th><td><%=name%></td>
            </tr>
            <tr>
                <th>성별</th><td><%=gender%></td>
            </tr>
            <tr>
                <th>나이</th><td><%=age%></td>
            </tr>
            <tr>
                <th>등록일</th><td><%=date%></td>
            </tr>
        </tbody>
    </table>
    <ul>
        <li><a href="/student_war_exploded/student/list.do">리스트</a></li>
        <li>
            <!-- todo ${update_link} 설정 c:url -->
            <a href="${update_link}">수정</a>
        </li>
        <li>
            <!-- todo 삭제버튼 구현, method=post -->
            <form action="/student_war_exploded/student/delete.do" method="post">
                <input type="hidden" name="removeId" value="<%=id%>">
                <button type="submit">삭제</button>
            </form>
        </li>

    </ul>
<%
httpSession.removeAttribute("id");
httpSession.removeAttribute("name");
httpSession.removeAttribute("gender");
httpSession.removeAttribute("age");
httpSession.removeAttribute("date");
%>
</body>
</html>