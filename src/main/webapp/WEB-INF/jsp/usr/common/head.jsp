<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="ko">

        <head>
            <meta charset="UTF-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>${pageTitle}</title>

            <!-- 제이쿼리 불러오기 -->
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

            <!-- 테일윈드 불러오기 -->
            <!-- 노말라이즈, 라이브러리까지 한방에 해결 -->
            <script src="https://unpkg.com/tailwindcss-jit-cdn"></script>

            <!-- daisy ui -->
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/daisyui/2.0.9/full.css"
                integrity="sha512-0JSLdMCGjtFivjqwnLASPcDjuajA4VSHkz19z+SPyOc/b808txD2DHZut3ipkY6Zj9ncYUjT+SHynWF3rmPISA=="
                crossorigin="anonymous" referrerpolicy="no-referrer" />

            <!-- 폰트어썸 불러오기 -->
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />

            <!-- 사이트 공통 CSS -->
            <link rel="stylesheet" href="/resource/common.css" />
            <!-- 사이트 공통 JS -->
            <script src="/resource/common.js" defer="defer"></script>
        </head>

        <body class="min-w-[500px]">
            <header class="navbar">
                <div class="flex-1">
                    <a href="/" class="btn btn-ghost nomal-case text-xl">로고</a>
                </div>
                <div class="flex-none">
                    <ul class="menu menu-horizontal p-0">
                        <li><a href="/">홈</a></li>
                        <li><a href="/usr/article/list">리스트</a></li>
                        <c:if test="${rq.isLogined()}">
                            <li><a href="/usr/article/Add">글 작성</a></li>
                            <li><a href="/usr/member/doLogout">로그아웃</a></li>
                        </c:if>
                        <c:if test="${rq.isLogined() == false}">
                            <li><a href="/usr/member/login">로그인</a></li>
                        </c:if>
                    </ul>
                </div>
            </header>
            <hr />
            <main class="container mx-auto px-5 mt-5 pb-[5rem]">
                <h1 class="text-3xl mb-5">${pageTitle}</h1>