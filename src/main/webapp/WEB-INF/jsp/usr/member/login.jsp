<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="로그인" />
        <%@ include file="../common/head.jspf" %>
            <div class="flex items-center justify-center min-h-[75vh] bg-base-300 rounded-md">
                <div class="flex justify-center px-8 py-6 text-left bg-base-100 min-w-[30vw]">
                    <h3 class="text-2xl font-bold text-center"></h3>
                    <form action="../member/doLogin" method="post">
                        <input type="hidden" name="afterLoginUri" value="${param.afterLoginUri}">
                        <div class="flex flex-col justify-center py-5 px-auto">
                            <div class="form-control">
                                <div class="w-10 bg-white">qwe${param.afterLoginUri}</div>
                                <label for="loginId">
                                    <span>아이디</span>
                                </label>
                                <input type="text" placeholder="Id" name="loginId"
                                    class="h-[4em] w-[30em] px-4 py-2 text-black border base_border_color border-opacity-20">
                            </div>
                            <div class="form-control mt-5 ">
                                <label for="loginPw">
                                    <span>비밀번호</span>
                                </label>
                                <input type="password" placeholder="Password" name="loginPw"
                                    class="h-[4em] w-[30em] px-4 py-2 text-black border base_border_color border-opacity-20">
                            </div>
                            <button type="submit"
                                class="text-[1.1rem] text-white font-semibold bg-blue-500 hover:bg-blue-600 mt-5 h-[4rem] w-[30rem] px-4 py-2 focus:outline-none focus:ring-1 focus:ring-blue-600">
                                로그인
                            </button>
                            <div class="mt-4 flex justify-start base_text_color font-semibold">
                                <a class="hover:text-blue-400 mr-5" href="#">회원가입</a>
                                <a class="hover:text-blue-400" href="#">아이디 /
                                    비밀번호 찾기</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <%@ include file="../common/foot.jspf" %>