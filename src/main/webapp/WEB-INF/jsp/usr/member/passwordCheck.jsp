<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="비밀번호 확인" />
        <%@ include file="../common/head.jspf" %>
            <div class="flex items-center justify-center min-h-[75vh] bg-base-200 rounded-md">
                <div class="flex justify-center px-8 py-6 text-left bg-base-100 shadow-lg min-w-[30vw]">
                    <h3 class="text-2xl font-bold text-center"></h3>
                    <form action="../member/doPasswordCheck" method="POST">
                        <input type="hidden" name="replaceUri" value="${param.replaceUri}">
                        <div class="flex flex-col justify-center py-5 px-auto">
                            <div class="flex items-center bg-white h-[4em] w-[30em] px-4 py-2 text-black opacity-75">
                                ${rq.loginedMemberNow.loginId}
                            </div>
                            <input type="password" placeholder="Password" name="loginPw"
                                class="mt-5 h-[4em] w-[30em] px-4 py-2 text-black">
                            <button type="submit"
                                class="bg-blue-500 hover:bg-blue-600 mt-5 h-[4em] w-[30em] px-4 py-2 focus:outline-none focus:ring-1 focus:ring-blue-600">
                                비밀번호 확인
                            </button>
                            <div class="mt-4 flex justify-end">
                                <a class="text-white hover:text-blue-400" href="javascript:history.back()">뒤로가기</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <%@ include file="../common/foot.jspf" %>