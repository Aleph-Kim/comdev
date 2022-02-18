<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ tagliburi="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>${pageTitle}</title>
        <link rel="stylesheet" href="/resource/common.css" />
    </head>
    <body>
        <h1>${pageTitle}</h1>
        <header>
            <a href="/">로고</a>
            <ul>
                <li><a href="/">홈</a></li>
                <li><a href="/usr/article/list">리스트</a></li>
            </ul>
        </header>
        <hr />
        <main>