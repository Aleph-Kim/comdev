<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="게시물 수정" />

        <%@ include file="../common/head.jspf" %>
            <%@ include file="../../common/toastUiEditorLib.jspf" %>

                <script>

                    let ArticleModify__submitDone = false;

                    function ArticleModify__submit(form) {
                        const editor = $(form).find('.toast-ui-editor').data('data-toast-editor');
                        const markdown = editor.getMarkdown().trim();

                        if (markdown.length == 0) {
                            alert("내용을 입력해주세요.");
                            editor.focus();
                            return;
                        }
                        form.body.value = markdown;

                        ArticleModify__submitDone = ture;
                        form.submit();
                    }

                </script>
                <form action="../article/doModify" method="post" onsubmit="ArticleModify__submit(this); return false;">
                    <input type="hidden" name="id" value="${article.id}" />
                    <input type="hidden" name="body" />
                    <div>
                        <table class="table w-full border border_table">
                            <colgroup>
                                <col width="200" />
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>게시물 번호</th>
                                    <td>${article.id}</td>
                                </tr>
                                <tr>
                                    <th>작성날짜</th>
                                    <td>${article.forPrintType2RegDate}</td>
                                </tr>
                                <tr>
                                    <th>게시물 수정날짜</th>
                                    <td>${article.forPrintType2UpdateDate}</td>
                                </tr>
                                <tr>
                                    <th>게시물 작성자</th>
                                    <td>${article.extra__writerName}</td>
                                </tr>
                                <tr>
                                    <th>좋아요</th>
                                    <td>${article.extra__LikePoint}</td>
                                </tr>
                                <tr>
                                    <th>게시물 제목</th>
                                    <td class="!p-0">
                                        <input type="text" value="${article.title}" name="title"
                                            class="text-black w-full h-[3.5rem]" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>게시물 내용</th>
                                    <td class="!p-0">
                                        <div class="toast-ui-editor">
                                            <script type="text/x-template">
                                                ${article.body}
                                            </script>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="flex justify-end mt-2">
                        <button class="btn point_bg mx-2" type="submit">확인</button>
                        <button class="btn point_bg" onclick="history.back();">뒤로가기</button>
                    </div>
                </form>

                <%@ include file="../common/foot.jspf" %>