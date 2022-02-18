<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ tagliburi="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<c:set var="pageTitle" value="게시물 리스트" />
<%@ include file="../common/head.jsp" %>
<div class="overflow-x-auto">
    <table class="table w-full">
        <thead>
            <tr>
                <th>번호</th>
                <th>날짜</th>
                <th>제목</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="article" items="${articles}">
                <a href="../article/detail?id=${article.id}.jsp">
                    <tr class="hover">
                        <td>${article.id}</td>
                        <td>${article.regDate.substring(2, 10)}</td>
                        <td>${article.title}</td>
                    </tr>
                </a>
            </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../common/foot.jsp" %>
