<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="게시물 상세페이지 (${board.name})" />

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
                <div class="overflow-y-auto w-full bg-gray-100/50">
                    <div class="flex items-center">
                        <div class="text-3xl font-bold mr-4">${article.title}</div>
                        <div class="text-lg mr-4">${article.extra__writerName}</div>
                        <div class="mr-4">${article.forPrintType2RegDate}</div>
                        <div class="flex items-center article-detail__hit-count mr-4">
                            <i class="fa-regular fa-eye"></i>
                            <div class="ml-1">${article.hitCount}</div>
                        </div>
                        <div class="mr-4 flex items-center">
                            <c:choose>
                                <c:when test="${rq.loginedMemberId > -1 && actorCanMakeLikePoint}">
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
                            <span class="ml-1">${article.extra__LikePoint}</span>
                        </div>
                    </div>
                        <div class="toast-ui-viewer">
                            <script type="text/x-templates">
                                ${article.body}
                            </script>
                        </div>
                    </div>
                    <div class="flex flex-row-reverse mb-3">
                        <button class="btn" onclick="history.back();">뒤로가기</button>
                        <c:if test="${article.memberId == rq.loginedMemberId}">
                            <a onclick="if (!confirm('정말 삭제하시겠습니까?')) return false;"
                                href="/usr/article/doDelete?id=${article.id}&boardId=${board.id}" class="btn mx-2">
                                삭제
                            </a>
                            <a href="/usr/article/modify?id=${article.id}" class="btn">수정</a>
                        </c:if>
                    </div>
                </div>
                <div class="mt-6 border-t w-full">
                    <div class="mt-3 w-full">
                        <form action="../reply/doWrite" method="POST"
                            onsubmit='ReplyWrite__submitForm(this); return false;'>
                            <input type="hidden" name="articleId" value="${article.id}">
                            <div class="text-[1.3rem]">댓글 작성</div>
                            <c:choose>
                                <c:when test="${rq.loginedMemberId > -1}">
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
                            <c:if test="${reply.memberId == rq.loginedMemberId}">
                                <div class="modifyReply-${reply.id} hidden mt-3">
                                    <form action="../reply/doModify">
                                        <span class="flex items-center">
                                            <input type="hidden" name="replyId" value="${reply.id}">
                                            <input type="hidden" name="articleId" value="${article.id}">
                                            <textarea class="textarea textarea-bordered w-[95%]"
                                                name="body">${reply.body}</textarea>
                                            <div class="flex flex-col ml-3">
                                                <button class="btn whitespace-nowrap bg-gray-700">수정</button>
                                                <input type="button" class="modify-close-btn btn mt-1 bg-gray-700"
                                                    value="취소" onsubmit="return false;"
                                                    onclick="modify_close('${reply.id}')" />
                                            </div>
                                        </span>
                                    </form>
                                </div>
                            </c:if>
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
                                    <c:if test="${reply.memberId == rq.loginedMemberId}">
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
                        <c:when test="${rq.loginedMemberId > -1 && actorCanMakeLikePoint}">
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