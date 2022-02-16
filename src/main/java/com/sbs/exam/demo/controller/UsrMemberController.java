package com.sbs.exam.demo.controller;

import javax.servlet.http.HttpSession;

import com.sbs.exam.demo.service.MemberService;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Member;
import com.sbs.exam.demo.vo.ResultData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrMemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("/usr/member/doJoin")
    @ResponseBody
    public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
            String email) {
        if (Ut.empty(loginId)) {
            return ResultData.from("F-21", "loginId을(를) 입력해주세요.");
        }
        if (Ut.empty(loginPw)) {
            return ResultData.from("F-22", "loginPw을(를) 입력해주세요.");
        }
        if (Ut.empty(name)) {
            return ResultData.from("F-23", "name을(를) 입력해주세요.");
        }
        if (Ut.empty(nickname)) {
            return ResultData.from("F-24", "nickname을(를) 입력해주세요.");
        }
        if (Ut.empty(email)) {
            return ResultData.from("F-25", "email을(를) 입력해주세요.");
        }
        ResultData<Member> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
        Member member = memberService.searchUserId(memberService.LastInsertId());

        if (joinRd.isFail()) {
            return joinRd;
        }
        return ResultData.newData(joinRd, member);
    }

    @RequestMapping("/usr/member/doLogin")
    @ResponseBody
    public ResultData<Member> doLogin(HttpSession httpSession, String loginId, String loginPw) {

        if (httpSession.getAttribute("LoginedMemberId") != null) {
            return ResultData.from("F-5", "이미 로그인 되어있습니다.");
        }
        if (Ut.empty(loginId)) {
            return ResultData.from("F-1", "loginId을(를) 입력해주세요.");
        }
        if (Ut.empty(loginPw)) {
            return ResultData.from("F-2", "loginPw을(를) 입력해주세요.");
        }

        Member member = memberService.SearchUserLoginId(loginId);

        if (member == null) {
            return ResultData.from("F-3", Ut.f("존재하지 않는 아이디 입니다.(%s)", loginId));
        }
        if (member.getLoginPw().equals(loginPw) == false) {
            return ResultData.from("F-4", "잘못된 비밀번호 입니다.");
        }
        httpSession.setAttribute("LoginedMemberId", member.getId());
        return ResultData.from("S-1", Ut.f("환영합니다 %s님", member.getNickname()));
    }

    @RequestMapping("/usr/member/doLogout")
    @ResponseBody
    public ResultData<Member> doLogout(HttpSession httpSession) {
        if (httpSession.getAttribute("LoginedMemberId") == null) {
            return ResultData.from("S-6", "로그인 후 이용해주세요.");
        }
        httpSession.removeAttribute("LoginedMemberId");
        return ResultData.from("S-1", "정상적으로 로그아웃 되었습니다.");
    }
}
