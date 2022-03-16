<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="게시물 상세페이지" />

        <%@ include file="../common/head.jspf" %>

            <script>
                const params = {};
                params.id = parseInt('${ param.id }');

                function ArticleDetail__increaseHitCount() {
                    $.get('../article/doIncreaseHitCount', {
                        id: params.id,
                        ajaxMode: 'Y'
                    }, function (data) {
                        $('.article-detail__hit-count').empty().html(data.data1);
                    }, 'json');
                }

                $(function () {
                    const localStorageKey = 'article__' + params.id + '__viewDone';

                    if (localStorage.getItem(localStorageKey)) {
                        return;
                    }

                    localStorage.setItem(localStorageKey, true);

                    ArticleDetail__increaseHitCount();
                })
            </script>

            <div class="overflow-y-auto">
                <table class="table w-full border_table">
                    <colgroup>
                        <col width="200" />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>게시물 번호</th>
                            <td>${article.id}</td>
                        </tr>
                        <tr>
                            <th>게시판 이름</th>
                            <td>${board.name}</td>
                        </tr>
                        <tr>
                            <th>작성날짜</th>
                            <td>${article.forPrintType2RegDate}</td>
                        </tr>
                        <tr>
                            <th>수정날짜</th>
                            <td>${article.forPrintType2UpdateDate}</td>
                        </tr>
                        <tr>
                            <th>작성자</th>
                            <td>${article.extra__writerName}</td>
                        </tr>
                        <tr>
                            <th>조회수</th>
                            <div class="article-detail__hit-count">
                                <td>${article.hitCount}</td>
                            </div>
                        </tr>
                        <tr>
                            <th>좋아요</th>
                            <td>${article.extra__goodLikePoint}</td>
                        </tr>
                        <tr>
                            <th>싫어요</th>
                            <td>${article.extra__badLikePoint}</td>
                        </tr>
                        <tr>
                            <th>제목</th>
                            <td>${article.title}</td>
                        </tr>
                        <tr>
                            <th>내용</th>
                            <td>${article.body}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="flex flex-row-reverse mt-3">
                <button class="btn" onclick="history.back();">뒤로가기</button>
                <c:if test="${article.memberId == sessionScope.LoginedMember.id}">
                    <a onclick="if (confirm('정말 삭제하시겠습니까?') == false);" href="/usr/article/doDelete?id=${article.id}"
                        class="btn mx-2">
                        삭제
                    </a>
                    <a href="/usr/article/modify?id=${article.id}" class="btn">수정</a>
                </c:if>
            </div>
            <c:if test="${actorCanMakeLikePoint}">
                <div class="flex justify-center mt-5">
                    <a href="" class="btn mr-2">좋아요</a>
                    <a href="" class="btn ml-2">싫어요</a>
                </div>
            </c:if>

            <%@ include file="../common/foot.jspf" %>