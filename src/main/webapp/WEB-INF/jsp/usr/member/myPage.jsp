<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="마이페이지" />

        <%@ include file="../common/head.jspf" %>
            <div class="overflow-y-auto">
                <table class="table w-full border_table">
                    <colgroup>
                        <col width="200" />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>아이디</th>
                            <td>${member.loginId}</td>
                        </tr>
                        <tr>
                            <th>비밀번호</th>
                            <td>
                                <a href="#" class="btn">비밀번호 변경</a>
                            </td>
                        </tr>
                        <tr>
                            <th>이름</th>
                            <td>${member.name}</td>
                        </tr>
                        <tr>
                            <th>닉네임</th>
                            <td>
                                <span class="mr-3"> ${member.nickname} </span>
                                <a href="#" class="btn">변경하기</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="flex justify-end">
                    <button class="btn mt-3" onclick="history.back();">뒤로가기</button>
                </div>
            </div>
            <%@ include file="../common/foot.jspf" %>