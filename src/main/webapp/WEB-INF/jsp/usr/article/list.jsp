<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="${board.name}" />
        <%@ include file="../common/head.jspf" %>
            <div class="flex justify-between">
                <div class="flex items-center">총 게시물 개수 : ( ${articlesCount} )</div>
                <div class="form-control mb-3">
                    <form action="/usr/article/list">
                        <div class="input-group">
                            <select class="select select-bordered" name="searchKeywordType">
                                <option selected value="titlebody">제목 + 내용</option>
                                <option value="title">제목</option>
                                <option value="body">내용</option>
                            </select>
                            <input type="text" name="searchKeyword" value="${param.searchKeyword}"
                                class="bg-base-200 text-black">
                            <input type="hidden" name="boardId" value="${param.boardId}">
                            <button class="btn">검색</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="overflow-x-auto">
                <table class="table w-full text-center border border-gray-500">
                    <colgroup>
                        <col width="50" />
                        <col width="" />
                        <col width="250" />
                        <col width="150" />
                        <col width="50" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th class="border border-gray-500 !z-[-1]">번호</th>
                            <th class="border border-gray-500">제목</th>
                            <th class="border border-gray-500">날짜</th>
                            <th class="border border-gray-500">작성자</th>
                            <th class="border border-gray-500">좋아요</th>
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
                                    ${article.forPrintType1RegDate}
                                </td>
                                <td class="border border-gray-500 !z-[-1]">
                                    ${article.extra__writerName}
                                </td>
                                <td class="border border-gray-500 !z-[-1]">
                                    ${article.extra__sumLikePoint}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="page-menu mt-5">
                <div class="btn-group justify-center">
                    <c:set var="pageMenuArmLen" value="4" />
                    <c:set var="pageStart" value="${page - pageMenuArmLen <= 0 ? 1 : page - pageMenuArmLen }" />
                    <c:set var="pageEnd"
                        value="${pageCount < (page + pageMenuArmLen) ? pageCount : (page + pageMenuArmLen) }" />

                    <c:set var="pageBaseUri"
                        value="?boardId=${board.id}&searchKeyword=${param.searchKeyword}&searchKeywordType=${param.searchKeywordType}" />

                    <c:if test="${pageStart != 1}">
                        <a class="btn " href="${pageBaseUri}&page=1">첫 페이지</a>
                        <c:if test="${page - pageMenuArmLen > 2}">
                            <button class="btn">...</button>
                        </c:if>
                    </c:if>
                    <c:forEach var="i" begin="${pageStart}" end="${pageEnd}">
                        <a class="btn ${page == i ? 'btn-active' : '' }" href="${pageBaseUri}&page=${i}">${i}</a>
                    </c:forEach>
                    <c:if test="${page + pageMenuArmLen < pageCount - 1}">
                        <button class="btn pageStartMore">...</button>
                    </c:if>
                    <c:if test="${pageEnd != pageCount}">
                        <a class="btn" href="${pageBaseUri}&page=${pageCount}">끝 페이지</a>
                    </c:if>
                </div>
            </div>
            <%@ include file="../common/foot.jspf" %>