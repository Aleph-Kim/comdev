<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="로그인" />
        <%@ include file="../common/head.jsp" %>
            <div class="overflow-x-auto">
                <form action="../member/doLogin" method="post">
                    <input class="text-black" type="text" name="loginId" placeholder="아이디">
                    <input class="text-black" type="password" name="loginPw" placeholder="비밀번호">
                    <button onclick="submit" class="btn">로그인</button>
                    <button class="btn" onclick="history.back();">뒤로가기</button>
                </form>
            </div>
            <%@ include file="../common/foot.jsp" %>