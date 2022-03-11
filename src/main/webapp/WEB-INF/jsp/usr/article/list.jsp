<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="${board.name}" />
        <%@ include file="../common/head.jsp" %>
            <div>총 게시물 개수 : ( ${articlesCount} )</div>
            <div class="overflow-x-auto">
                <table class="table w-full text-center border border-gray-500">
                    <colgroup>
                        <col width="50" />
                        <col width="" />
                        <col width="250" />
                        <col width="150" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th class="border border-gray-500 !z-[-1]">번호</th>
                            <th class="border border-gray-500">제목</th>
                            <th class="border border-gray-500">날짜</th>
                            <th class="border border-gray-500">작성자</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="article" items="${articles}">
                            <tr class="hover cursor-pointer"
                                onclick="window.location.href='/usr/article/detail?id=${article.id}'">
                                <td class="border border-gray-500 !z-[-1]">
                                    ${article.id}
                                </td>
                                <td class="border border-gray-500 !z-[-1]">
                                    ${article.title}
                                </td>
                                <td class="border border-gray-500 !z-[-1]">
                                    ${article.regDate.substring(2, 10)}
                                </td>
                                <td class="border border-gray-500 !z-[-1]">
                                    ${article.extra__writerName}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="page-menu mt-5">
                <div class="btn-group justify-center">
                    <c:forEach var="i" begin="1" end="20">
                        <a class="btn ${param.page == i ? 'btn-active' : '' }"
                            href="?boardId=${param.boardId}&page=${i}">${i}</a>
                    </c:forEach>
                </div>
            </div>
            <%@ include file="../common/foot.jsp" %>