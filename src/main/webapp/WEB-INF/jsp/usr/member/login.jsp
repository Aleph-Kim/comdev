<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="로그인" />
        <%@ include file="../common/head.jspf" %>
            <div class="flex items-center justify-center min-h-[75vh] bg-base-200 rounded-md">
                <div class="px-8 py-6 mt-4 text-left bg-base-100 shadow-lg min-w-[30vw]">
                    <h3 class="text-2xl font-bold text-center"></h3>
                    <form action="../member/doLogin" method="post">
                        <div class="my-2">
                            <div class="flex flex-row items-center">
                                <span class="whitespace-nowrap mr-3 min-w-[50px]">아이디</span>
                                <input type="text" placeholder="Id" name="loginId"
                                    class="w-full px-4 py-2 border rounded-md text-black focus:outline-none focus:ring-1 focus:ring-blue-600">
                            </div>
                            <div class="flex flex-row items-center mt-2">
                                <span class="whitespace-nowrap mr-3">비밀번호</span>
                                <input type="password" placeholder="Password" name="loginPw"
                                    class="w-full px-4 py-2 border text-black rounded-md focus:outline-none focus:ring-1 focus:ring-blue-600">
                            </div>
                            <div class="flex items-baseline justify-between">
                                <div>
                                    <button
                                        class="px-6 py-2 mt-4 text-white bg-blue-500 rounded-md hover:bg-white hover:text-black">로그인</button>
                                    <a class="inline-block px-6 py-2 mt-4 text-white bg-blue-500 rounded-md hover:bg-white hover:text-black"
                                        href="#">회원가입</a>
                                    <a class="inline-block px-6 py-2 mt-4 text-white bg-blue-500 rounded-md hover:bg-white hover:text-black"
                                        href="#">아이디 / 비밀번호 찾기</a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <%@ include file="../common/foot.jspf" %>