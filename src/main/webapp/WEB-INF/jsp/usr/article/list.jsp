<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ tagliburi="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>게시물 리스트</title>
        <link rel="stylesheet" href="/resource/common.css" />
    </head>
    <body>
        <h1>게시물 리스트</h1>
        <header>
            <a href="/">로고</a>
            <ul>
                <li><a href="/">홈</a></li>
                <li><a href="/usr/article/list">리스트</a></li>
            </ul>
        </header>
        <hr />
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
                            <a href="../article/detail.jsp">
                                ${article.title}
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
