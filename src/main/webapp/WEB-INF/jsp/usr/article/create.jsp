<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="게시물 작성" />

        <%@ include file="../common/head.jsp" %>
            <form action="../article/doAdd" method="post">
                <input type="hidden" name="id" value="${article.id}" />
                <div>
                    <table class="table w-full border border_table text-lg">
                        <colgroup>
                            <col width="200" />
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>게시물 작성자</th>
                                <td>${sessionScope.LoginedMember.nickname}</td>
                            </tr>
                            <tr>
                                <th>게시물 제목</th>
                                <td class="!p-0">
                                    <input type="text" value="${article.title}" name="title" placeholder="제목"
                                        class="text-black text-lg w-full h-[3.5rem]" />
                                </td>
                            </tr>
                            <tr>
                                <th>게시물 내용</th>
                                <td class="!p-0">
                                    <input type="text" value="${article.body}" rows="10" name="body" placeholder="내용"
                                        class="text-black text-lg w-full min-h-[10rem]" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="flex justify-end mt-2">
                    <button class="btn mx-2" type="submit">생성</button>
                    <button class="btn" onclick="history.back();">취소</button>
                </div>
            </form>

            <%@ include file="../common/foot.jsp" %>