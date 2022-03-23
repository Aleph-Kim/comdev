<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ tagliburi="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:set var="pageTitle" value="회원가입" />
        <%@ include file="../common/head.jspf" %>


            <script>
                let MemberJoin__submitDone = false;
                let validLoginId = "";

                function MemberJoin__submit(form) {
                    console.log(form.email);
                    if (MemberJoin__submitDone) {
                        return;
                    }

                    form.loginId.value = form.loginId.value.trim();

                    if (form.loginId.value.length == 0) {
                        alert('아이디를 입력해주세요.');
                        form.loginIdCheck.focus();

                        return;
                    }

                    if (form.loginId.value != validLoginId) {
                        alert("사용할 수 없는 아이디입니다.");
                        form.loginId.focus();
                        return;
                    }

                    form.loginPw.value = form.loginPw.value.trim();
                    form.loginPwCheck.value = form.loginPwCheck.value.trim();

                    if (form.loginPw.value.length > 0) {

                        if (form.loginPwCheck.value.length == 0) {
                            alert('비밀번호 확인을 입력해주세요.');
                            form.loginPwCheck.focus();

                            return;
                        }

                        if (form.loginPw.value != form.loginPwCheck.value) {
                            alert('비밀번호 확인이 일치하지 않습니다.');
                            form.loginPwCheck.focus();

                            return;
                        }

                    } else {
                        alert('비밀번호를 입력해주세요.');
                        form.loginPw.focus();

                        return;
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

                    MemberJoin__submitDone = true;
                    form.submit();

                }

                function checkLoginIdDup(el) {
                    $('.loginId-message').empty();
                    const form = $(el).closest('form').get(0);

                    if (form.loginId.value.length == 0) {
                        validLoginId = '';
                        return;
                    }

                    $.get('../member/getLoginIdDup', {
                        isAjax: 'Y',
                        loginId: form.loginId.value
                    }, function (data) {
                        $('.loginId-message').html('<div class="mt-2">' + data.msg + '</div>');
                        if (data.succsess) {
                            validLoginId = data.data1;
                        } else {
                            validLoginId = '';
                        }
                    }, 'json');
                }

            </script>

            <form action="../member/doJoin" method="POST" onsubmit="MemberJoin__submit(this); return false;">
                <table class="table w-full border_table">
                    <colgroup>
                        <col width="200" />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>아이디</th>
                            <td>
                                <input type="text"
                                    class="inline-block text-black w-[40%] h-12 border base_border_color border-opacity-20"
                                    placeholder="new id" name="loginId" onkeyup="checkLoginIdDup(this);"
                                    autocomplete="off">
                                <div class="loginId-message inline-block ml-3"></div>
                            </td>
                        </tr>
                        <tr>
                            <th>비밀번호</th>
                            <td>
                                <input type="password"
                                    class="text-black w-[40%] h-12 border base_border_color border-opacity-20"
                                    placeholder="new password" name="loginPw">
                            </td>
                        </tr>
                        <tr>
                            <th>비밀번호 확인</th>
                            <td>
                                <input type="password"
                                    class="text-black w-[40%] h-12 border base_border_color border-opacity-20"
                                    placeholder="password check" name="loginPwCheck">
                            </td>
                        </tr>
                        <tr>
                            <th>이름</th>
                            <td>
                                <div>
                                    <input type="text"
                                        class="text-black w-[40%] h-12 border base_border_color border-opacity-20 "
                                        placeholder="name" name="name">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>닉네임</th>
                            <td>
                                <div>
                                    <input type="text"
                                        class="text-black w-[40%] h-12 border base_border_color border-opacity-20 "
                                        placeholder="nickname" name="nickname">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>전화번호</th>
                            <td>
                                <div>
                                    <input type="text"
                                        class="text-black w-[40%] h-12 border base_border_color border-opacity-20 "
                                        placeholder="cellphoneNo" id="cellphoneNoInput" name="cellphoneNo">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>이메일</th>
                            <td>
                                <div>
                                    <input type="email" required="required"
                                        class="text-black w-[40%] h-12 border base_border_color border-opacity-20 "
                                        placeholder="email" name="email">
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="mt-4 flex justify-end">
                    <button class="btn mr-3">가입하기</button>
                    <a href="javascript:history.back()" class="btn">뒤로가기</a>
                </div>
            </form>
            <%@ include file="../common/foot.jspf" %>