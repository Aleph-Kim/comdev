<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="ko" data-theme="emerald">

        <head>
            <meta charset="UTF-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>COM - DEV</title>

            <!-- 제이쿼리 불러오기 -->
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

            <!-- 테일윈드 불러오기 -->
            <!-- 노말라이즈, 라이브러리까지 한방에 해결 -->
            <script src="https://cdn.tailwindcss.com"></script>

            <!-- daisy ui -->
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/daisyui/2.0.9/full.css"
                integrity="sha512-0JSLdMCGjtFivjqwnLASPcDjuajA4VSHkz19z+SPyOc/b808txD2DHZut3ipkY6Zj9ncYUjT+SHynWF3rmPISA=="
                crossorigin="anonymous" referrerpolicy="no-referrer" />

            <!-- 폰트어썸 불러오기 -->
            <link rel="stylesheet"
                href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css">

            <!-- 사이트 공통 CSS -->
            <link rel="stylesheet" href="/resource/common.css" />

            <!-- 사이트 공통 JS -->
            <script src="/resource/common.js" defer="defer"></script>
        </head>

        <body class="min-w-[800px] h-screen inset-0 text-white">
            <header class="fixed navbar">
                <div class="flex-1">
                    <a href="/" class="btn btn-ghost nomal-case text-lg font-bold text-[#07553B]">COM - Dev</a>
                </div>
                <div class="flex-none text-[#CED46A]">
                    <a class="btn btn-ghost nomal-case text-lg " href="/">홈</a>
                    <div class="dropdown dropdown-hover text-lg point_color">
                        <label tabindex="0" class="btn btn-ghost text-lg rounded-btn">게시판</label>
                        <ul tabindex="0" class="dropdown-content menu p-2 bg-[#d3d3d390] rounded-box w-52 absolute right-0">
                            <li><a href="/usr/article/list?boardId=1">공지 게시판</a></li>
                            <li><a href="/usr/article/list?boardId=1">자유 게시판</a></li>
                            <li><a href="/usr/article/list?boardId=1">팁 게시판</a></li>
                            <li><a href="/usr/article/list?boardId=1">테크 게시판</a></li>
                        </ul>
                    </div>
                    <c:if test="${rq.isLogined()}">
                        <a class="btn btn-ghost nomal-case text-lg" href="/usr/article/Add">글 작성</a>
                        <a class="btn btn-ghost nomal-case text-lg"
                            href="/usr/member/myPage?id=${rq.loginedMemberId}">마이페이지</a>
                        <a class="btn btn-ghost nomal-case text-lg" href="${rq.logoutUri}">로그아웃</a>
                    </c:if>
                    <c:if test="${rq.isLogined() == false}">
                        <a class="btn btn-ghost nomal-case text-lg" href="${rq.loginUri}">로그인</a>
                    </c:if>
                </div>
            </header>
            <div class="bg-[url('/resource/main_image.jpg')] bg-[#21212180] bg-blend-darken h-full w-full">
                <div class="h-full w-full flex items-center justify-center">
                    <div class="">
                        <div class="text-[7rem]">
                            <span class="text-[10rem] font-bold text-[#07553B]">COM</span>unity
                        </div>
                        <div class="text-[7rem]">
                            <span class="text-[10rem] font-bold text-[#CED46A]">DEV</span>eloper
                        </div>
                        <div class="text-2xl">COM-DEV is a space where developers can freely share code.</div>
                    </div>
                </div>

            </div>
            <%@ include file="../common/foot.jspf" %>