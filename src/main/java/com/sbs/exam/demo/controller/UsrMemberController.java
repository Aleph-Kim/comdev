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
    public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
            String email) {
        if (Ut.empty(loginId)) {
            return Ut.jsHistoryBack("아이디를 입력해주세요.");
        }
        if (Ut.empty(loginPw)) {
            return Ut.jsHistoryBack("비밀번호를 입력해주세요.");
        }
        if (Ut.empty(name)) {
            return Ut.jsHistoryBack("이름을 입력해주세요.");
        }
        if (Ut.empty(nickname)) {
            return Ut.jsHistoryBack("닉네임을 입력해주세요.");
        }
        if (Ut.empty(email)) {
            return Ut.jsHistoryBack("이메일을 입력해주세요.");
        }
        ResultData<Member> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
        Member member = memberService.searchUserId(memberService.LastInsertId());

        if (joinRd.isFail()) {
            return Ut.jsHistoryBack(joinRd.getMsg());
        }

        ResultData.newData(joinRd, member);
        return Ut.jsReplace(Ut.f("환영합니다. %s님!", member.getNickname()), "/");
    }

    @RequestMapping("/usr/member/login")
    public String showLogin(HttpSession httpSession) {
        return "/usr/member/login";
    }

    @RequestMapping("/usr/member/doLogin")
    @ResponseBody
    public String doLogin(HttpSession httpSession, String loginId, String loginPw) {

        if (httpSession.getAttribute("LoginedMemberId") != null) {
            return Ut.jsHistoryBack("이미 로그인 되어있습니다.");
        }
        if (Ut.empty(loginId)) {
            return Ut.jsHistoryBack("아이디를 입력해주세요.");
        }
        if (Ut.empty(loginPw)) {
            return Ut.jsHistoryBack("비밀번호를 입력해주세요.");
        }

        Member member = memberService.SearchUserLoginId(loginId);

        if (member == null) {
            return Ut.jsHistoryBack(Ut.f("존재하지 않는 아이디 입니다.(%s)", loginId));
        }
        if (member.getLoginPw().equals(loginPw) == false) {
            return Ut.jsHistoryBack("잘못된 비밀번호 입니다.");
        }
        httpSession.setAttribute("LoginedMemberId", member.getId());
        return Ut.jsReplace(Ut.f("환영합니다 %s님", member.getNickname()), "/");
    }

    @RequestMapping("/usr/member/doLogout")
    @ResponseBody
    public String doLogout(HttpSession httpSession) {
        if (httpSession.getAttribute("LoginedMemberId") == null) {
            return Ut.jsHistoryBack("로그인 후 이용해주세요.");
        }
        httpSession.removeAttribute("LoginedMemberId");
        return Ut.jsReplace("정상적으로 로그아웃 되었습니다.", "../member/login");
    }
}
