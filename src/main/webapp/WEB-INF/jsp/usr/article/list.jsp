<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ tagliburi="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<c:set var="pageTitle" value="게시물 리스트" />
<%@ include file="../common/head.jsp" %>
<table border="1">
    <thead>
        <tr>
            <th>번호</th>
            <th>날짜</th>
            <th>제목</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="article" items="${articles}">
            <tr>
                <td>${article.id}</td>
                <td>${article.regDate.substring(2, 10)}</td>
                <td>
                    <a href="../article/detail.jsp"> ${article.title} </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<%@ include file="../common/foot.jsp" %>