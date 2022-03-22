<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="게시물 작성" />

        <%@ include file="../common/head.jspf" %>
            <%@ include file="../../common/toastUiEditorLib.jspf" %>
                <script>

                    let submitWriteFormDone = false;

                    function submitWriteForm(form) {
                        if (submitWriteFormDone) {
                            alert("처리중입니다.");
                            return;
                        }
                        form.title.value = form.title.value.trim();

                        if (form.title.value.length == 0) {
                            alert("제목을 입력해주세요");
                            form.title.focus();
                            return;
                        }

                        const editor = $(form).find('.toast-ui-editor').data('data-toast-editor');
                        const markdown = editor.getMarkdown().trim();
                        if (markdown.length == 0) {
                            alert('내용을 입력해주세요.');
                            editor.focus();
                            return;
                        }

                        form.body.value = markdown;

                        form.submit();

                        submitWriteFormDone = true;
                    }
                </script>
                <form action="../article/doAdd" method="post" onsubmit="submitWriteForm(this); return false;">
                    <input type="hidden" name="id" value="${article.id}" />
                    <input type="hidden" name="body" />
                    <div>
                        <table class="table w-full border border_table text-lg">
                            <colgroup>
                                <col width="200" />
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>게시물 작성자</th>
                                    <td>${rq.loginedMemberNow.nickname}</td>
                                </tr>
                                <tr>
                                    <th>게시판 선택</th>
                                    <td class="!p-0">
                                        <select class="select select-bordered w-full max-w-xs bg-base-200"
                                            name="boardId">
                                            <option readonly selected value="-1">-------- 게시판을 선택해주세요 --------</option>
                                            <option value="1">공지사항</option>
                                            <option value="2">자유게시판</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th>게시물 제목</th>
                                    <td class="!p-0">
                                        <input required="required" type="text" value="${article.title}" name="title"
                                            placeholder="제목" class="text-black text-lg w-full h-[3.5rem]" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>게시물 내용</th>
                                    <td class="!p-0">
                                        <div class="toast-ui-editor bg-gray-300">
                                            <script type="text/x-template"></script>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="flex justify-end mt-2">
                        <button class="btn mx-2 bg-gray-700" type="submit">생성</button>
                        <button class="btn" onclick="history.back();">취소</button>
                    </div>
                </form>

                <%@ include file="../common/foot.jspf" %>