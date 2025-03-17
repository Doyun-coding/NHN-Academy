<%@ page import="com.nhnacademy.student.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
  <title>학생-등록</title>
  <link rel="stylesheet" href="/style.css" />
  <meta charset="UTF-8" />
  <style>
    table, th, td {
      border: 1px solid black;
      border-collapse: collapse;
    }
    th, td {
      padding: 8px;
      text-align: center;
    }
  </style>
</head>

<body>
<!-- todo action 주소 설정
    //등록
        action = /student/register
    //수정
        action = /student/update
-->

  <c:choose>
    <c:when test="${empty student}">
      <c:set var="action" value="register.do" />
    </c:when>
    <c:otherwise>
      <c:set var="action" value="update.do" />
    </c:otherwise>
  </c:choose>

<form method="post" action="${action}">
  <table>
    <tbody>
    <tr>
      <th>ID</th>
      <td><input type="text" name="id" value="${student.id}" required /></td>
    </tr>
    <!-- todo input 구현 -->
    <tr>
      <th>이름</th>
      <td><input type="text" name="name" value="${student.name}" required /></td>
    </tr>
    <tr>
      <th>성별</th>
      <td><input type="radio" name="gender" value="M">남<input type="radio" name="gender" value="F">녀</td>
    </tr>
    <tr>
      <th>나이</th>
      <td><input type="text" name="age" value="${student.age}" required /></td>
    </tr>

    </tbody>
  </table>
  <p>
    <button type="submit">
      <c:choose>
        <c:when test="${empty student}">
          등록
        </c:when>
        <c:otherwise>
          수정
        </c:otherwise>
      </c:choose>
    </button>
    <tr>
      <th>${error_message}</th>
    </tr>
  </p>

</form>
</body>
</html>