<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ page import="com.comdev.exam.demo.util.Ut" %>
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
                                    <a href="../member/passwordCheck?replaceUri=${Ut.getUriEncoded('../member/modify')}"
                                        class="btn point_bg ">비밀번호 변경</a>
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
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="flex justify-end mt-3">
                        <a href="../member/passwordCheck?replaceUri=${Ut.getUriEncoded('../member/modify')}"
                            class="btn point_bg ">회원정보 수정</a>
                        <button class="btn point_bg  ml-4" onclick="history.back();">뒤로가기</button>
                    </div>
                </div>
                <%@ include file="../common/foot.jspf" %>