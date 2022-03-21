<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="회원정보 수정" />
        <%@ include file="../common/head.jspf" %>


            <script>
                let MemberModify__submitDone = false;
                function MemberModify__submit(form) {
                    console.log(MemberModify__submitDone);
                    if (MemberModify__submitDone) {
                        return;
                    }

                    form.newPassword.value = form.newPassword.value.trim();

                    if (form.newPassword.value.length > 0) {
                        form.newPasswordCheck.value = form.newPasswordCheck.value.trim();

                        if (form.newPasswordCheck.value.length == 0) {
                            alert('비밀번호확인을 입력해주세요.');
                            form.newPasswordCheck.focus();

                            return;
                        }

                        if (form.newPassword.value != form.newPasswordCheck.value) {
                            alert('비밀번호확인이 일치하지 않습니다.');
                            form.newPasswordCheck.focus();

                            return;
                        }
                    }

                    form.name.value = form.name.value.trim();

                    if (form.name.value.length == 0) {
                        alert('이름을 입력해주세요.');
                        form.name.focus();

                        return;
                    }

                    form.nickname.value = form.nickname.value.trim();

                    if (form.nickname.value.length == 0) {
                        alert('별명을 입력해주세요.');
                        form.nickname.focus();

                        return;
                    }

                    form.email.value = form.email.value.trim();

                    if (form.email.value.length == 0) {
                        alert('이메일을 입력해주세요.');
                        form.email.focus();

                        return;
                    }

                    form.cellphoneNo.value = form.cellphoneNo.value.trim();

                    if (form.cellphoneNo.value.length == 0) {
                        alert('번호를 입력해주세요.');
                        form.cellphoneNo.focus();

                        return;
                    }

                    MemberModify__submitDone = true;
                    form.submit();
                }
            </script>

            <form action="../member/doModify" method="POST" onsubmit="MemberModify__submit(this); return false;">
                <table class="table w-full border_table">
                    <colgroup>
                        <col width="200" />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>아이디</th>
                            <td>
                                <input type="text" value="${rq.loginedMemberNow.loginId}"
                                    class="text-black disabled:opacity-75 w-[40%]" disabled>
                            </td>
                        </tr>
                        <tr class="passwordModifyBtn">
                            <th>비밀번호</th>
                            <td>
                                <a class="btn" onclick="modify('password')">변경하기</a>
                            </td>
                        </tr>
                        <tr class="passwordModify hidden">
                            <th>새 비밀번호</th>
                            <td>
                                <input type="password" class="text-black w-[40%] newPasswordInput"
                                    placeholder="new password" name="newPassword">
                            </td>
                        </tr>
                        <tr class="passwordModify hidden">
                            <th>비밀번호 확인</th>
                            <td>
                                <input type="password" class="text-black w-[40%] newPasswordInput"
                                    placeholder="password check" name="newPasswordCheck">
                                <a class="btn"
                                    onclick="cancell('password'); javascript:$('.newPasswordInput').val('')">취소</a>
                            </td>
                        </tr>
                        <tr>
                            <th>이름</th>
                            <td>
                                <a class="btn nameModifyBtn" onclick="modify('name')">변경하기</a>
                                <div class="nameModify hidden">
                                    <c:set var="name" value="${rq.loginedMemberNow.name}" />
                                    <input type="text" value="${name}" class="text-black w-[40%]" placeholder="name"
                                        id="nameInput">
                                    <a class="btn"
                                        onclick="cancell('name'); javascript:$('#nameInput').val('${name}')">취소</a>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>닉네임</th>
                            <td>
                                <a class="btn nicknameModifyBtn" onclick="modify('nickname')">변경하기</a>
                                <div class="nicknameModify hidden">
                                    <c:set var="nickname" value="${rq.loginedMemberNow.nickname}" />
                                    <input type="text" value="${nickname}" class="text-black w-[40%]"
                                        placeholder="nickname" id="nicknameInput">
                                    <a class="btn"
                                        onclick="cancell('nickname'); javascript:$('#nicknameInput').val('${nickname}')">취소</a>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>전화번호</th>
                            <td>
                                <a class="btn cellphoneNoModifyBtn" onclick="modify('cellphoneNo')">변경하기</a>
                                <div class="cellphoneNoModify hidden">
                                    <c:set var="cellphoneNo" value="${rq.loginedMemberNow.cellphoneNo}" />
                                    <input type="text" value="${cellphoneNo}" class="text-black w-[40%]"
                                        placeholder="cellphoneNo" id="cellphoneNoInput">
                                    <a class="btn"
                                        onclick="cancell('cellphoneNo'); javascript:$('#cellphoneNoInput').val('${cellphoneNo}')">취소</a>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>이메일</th>
                            <td>
                                <a class="btn emailModifyBtn" onclick="modify('email')">변경하기</a>
                                <div class="emailModify hidden">
                                    <c:set var="email" value="${rq.loginedMemberNow.email}" />
                                    <input type="text" value="${email}" class="text-black w-[40%]" placeholder="email"
                                        id="emailInput">
                                    <a class="btn"
                                        onclick="cancell('email'); javascript:$('#emailInput').val('${email}')">취소</a>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="mt-4 flex justify-end">
                    <input type="submit" class="btn mr-3" value="수정완료" />
                    <a href="javascript:history.back()" class="btn">뒤로가기</a>
                </div>
            </form>

            <script>
                function modify(str) {
                    $(`.` + str + `Modify`).removeClass("hidden");
                    $(`.` + str + `ModifyBtn`).addClass("hidden");
                }

                function cancell(str) {
                    $(`.` + str + `Modify`).addClass("hidden");
                    $(`.` + str + `ModifyBtn`).removeClass("hidden");
                }
            </script>
            <%@ include file="../common/foot.jspf" %>