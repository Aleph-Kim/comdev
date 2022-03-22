<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="게시물 상세페이지" />

        <%@ include file="../common/head.jspf" %>
            <%@ include file="../../common/toastUiEditorLib.jspf" %>
                <script>
                    const params = {};
                    params.id = parseInt('${ param.id }');

                    function ArticleDetail__increaseHitCount() {
                        const localStorageKey = 'article__' + params.id + '__viewDone';

                        if (localStorage.getItem(localStorageKey)) {
                            return;
                        }

                        localStorage.setItem(localStorageKey, true);

                        $.get('../article/doIncreaseHitCount', {
                            id: params.id,
                            ajaxMode: 'Y'
                        }, function (data) {
                            $('.article-detail__hit-count').empty().html(data.data1);
                        }, 'json');
                    }

                    $(function () {
                        setTimeout(ArticleDetail__increaseHitCount, 500);
                    })
                </script>

                <script>
                    let ReplyWrite__submitFormDone = false;

                    function ReplyWrite__submitForm(form) {
                        if (ReplyWrite__submitFormDone) {
                            return true;
                        }

                        form.body.value = form.body.value.trim();

                        if (form.body.value.length == 0) {
                            alert('댓글을 입력해주세요.');
                            form.body.focus();
                            return "false";
                        }
                        if (form.body.value.length < 5) {
                            alert('댓글을 5글자 이상 입력해주세요.');
                            form.body.focus();
                            return false;
                        }

                        ReplyWrite__submitFormDone = true;
                        form.submit();
                    }
                </script>

                <div class="flex flex-row-reverse mb-3">
                    <button class="btn" onclick="history.back();">뒤로가기</button>
                    <c:if test="${article.memberId == rq.getLoginedMemberId()}">
                        <a onclick="if (!confirm('정말 삭제하시겠습니까?')) return false;"
                            href="/usr/article/doDelete?id=${article.id}&boardId=${board.id}" class="btn mx-2">
                            삭제
                        </a>
                        <a href="/usr/article/modify?id=${article.id}" class="btn">수정</a>
                    </c:if>
                </div>
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
                                <td class="">
                                    <c:choose>
                                        <c:when test="${sessionScope.LoginedMemberId > -1 && actorCanMakeLikePoint}">
                                            <a href="../article/doDecreaseLikePoint?articleId=${article.id}" class="">
                                                <span>🧡</span>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="../article/doIncreaseLikePoint?articleId=${article.id}" class="">
                                                <span>🤍</span>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                    <span>${article.extra__LikePoint}</span>
                                </td>
                            </tr>
                            <tr>
                                <th>제목</th>
                                <td>${article.title}</td>
                            </tr>
                            <tr>
                                <th>내용</th>
                                <td>
                                    <div class="toast-ui-viewer">
                                        <script type="text/x-templates">
                                            ${article.body}
                                        </script>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="mt-6 border-t w-full">
                    <div class="mt-3 w-full">
                        <form action="../reply/doWrite" method="POST"
                            onsubmit='ReplyWrite__submitForm(this); return false;'>
                            <input type="hidden" name="articleId" value="${article.id}">
                            <div class="text-[1.3rem]">댓글 작성</div>
                            <c:choose>
                                <c:when test="${sessionScope.LoginedMemberId > -1}">
                                    <div class="flex items-center">
                                        <textarea rows="5" placeholder="댓글을 입력해주세요."
                                            class="textarea textarea-bordered w-full min-h-[10rem] max-w-full my-2 text-lg"
                                            name="body"></textarea>
                                        <button class="btn ml-3">댓글 작성</button>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <textarea rows="5" placeholder="로그인 후 이용해주세요."
                                        class="textarea w-full min-h-[10rem] my-2" disabled></textarea>
                                    <div class="flex justify-end">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </div>
                </div>
                <div class="mt-6 border-t">
                    <div class="mt-3 w-full max-w-full border border-[#79797965] p-5 rounded">
                        <div class="text-[1.3rem]">댓글 : ${replyCount}</div>
                        <c:if test="${empty replies}">
                            <div class="mt-3">
                                댓글이 없습니다. 첫 댓글의 주인이 되어주세요!
                            </div>
                        </c:if>
                        <c:set var="replyCounter" value="1" />
                        <c:forEach var="reply" items="${replies}">
                            <div class="modifyReply-${reply.id} hidden">
                                <form action="../reply/doModify">
                                    <span class="flex items-center">
                                        <input type="hidden" name="replyId" value="${reply.id}">
                                        <input type="hidden" name="articleId" value="${article.id}">
                                        <textarea class="textarea textarea-bordered w-[95%]"
                                            name="body">${reply.body}</textarea>
                                        <div class="flex flex-col ml-3">
                                            <button class="btn">수정</button>
                                            <input type="button" class="modify-close-btn btn mt-2" value="취소"
                                                onsubmit="return false;" onclick="modify_close('${reply.id}')" />
                                        </div>
                                    </span>
                                </form>
                            </div>
                            <div
                                class="showReply-${reply.id} text-lg flex justify-between items-center min-h-[5rem] mt-3 px-5 hover:border hover:bg-gray-300 hover:text-black">
                                <div class="break-all p-3 max-w-[70%]">
                                    ${reply.body}
                                </div>
                                <div class="h-[5rem] flex items-center whitespace-nowrap">
                                    <span class="flex items-center h-full p-5">
                                        ${reply.extra__writerName}
                                    </span>
                                    <span class="flex items-center h-full p-5">
                                        ${reply.forPrintType1RegDate}
                                    </span>
                                    <c:if test="${article.memberId == sessionScope.LoginedMember.id}">
                                        <button class="btn mx-3" onclick="reply_modify('${reply.id}')">
                                            수정
                                        </button>
                                        <a class="btn"
                                            href="../reply/doDelete?replyId=${reply.id}&articleId=${article.id}">
                                            삭제
                                        </a>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="flex justify-center mt-5">
                    <c:choose>
                        <c:when test="${sessionScope.LoginedMemberId > -1 && actorCanMakeLikePoint}">
                            <a href="../article/doDecreaseLikePoint?articleId=${article.id}" class="flex items-center">
                                <span>좋아요</span>
                                <span class="text-[3rem] mb-1">🧡</span>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="../article/doIncreaseLikePoint?articleId=${article.id}" class="flex items-center">
                                <span>좋아요</span>
                                <span class="text-[3rem] mb-1">🤍</span>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <script>
                    function reply_modify(id) {
                        $(`.showReply-` + id).addClass("hidden");
                        $(`.modifyReply-` + id).removeClass("hidden");
                    };
                    function modify_close(id) {
                        $(`.modifyReply-` + id).addClass("hidden");
                        $(`.showReply-` + id).removeClass("hidden");
                    };
                </script>
                <%@ include file="../common/foot.jspf" %>