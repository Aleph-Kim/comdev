package com.sbs.exam.demo.controller;

import javax.servlet.http.HttpSession;

import com.sbs.exam.demo.service.MemberService;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Member;
import com.sbs.exam.demo.vo.ResultData;
import com.sbs.exam.demo.vo.Rq;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrMemberController {

    private MemberService memberService;
    private Rq rq;

    public UsrMemberController(MemberService memberService, Rq rq) {
        this.memberService = memberService;
        this.rq = rq;
    }

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
    public String doLogin(String loginId, String loginPw) {
        if (rq.isLogined()) {
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

        rq.login(member);

        return Ut.jsReplace(Ut.f("환영합니다 <%s>님", member.getNickname()), "/");
    }

    @RequestMapping("/usr/member/doLogout")
    @ResponseBody
    public String doLogout() {
        if (!rq.isLogined()) {
            return Ut.jsHistoryBack("로그인 후 이용해주세요.");
        }

        rq.logout();

        return Ut.jsReplace("정상적으로 로그아웃 되었습니다.", "../member/login");
    }

    @RequestMapping("/usr/member/myPage")
    public String showMyPage(HttpSession httpSession, Model model, int id) {

        if (id != rq.getLoginedMemberId()) {
            return rq.jsHistoryBackOnView("접근 권한이 없습니다.");
        }

        Member member = memberService.searchUserId(id);

        model.addAttribute("member", member);
        return "/usr/member/myPage";
    }

    @RequestMapping("/usr/member/passwordCheck")
    public String showPasswordCheck() {
        return "/usr/member/passwordCheck";
    }

    @RequestMapping("/usr/member/doPasswordCheck")
    @ResponseBody
    public String doPasswordCheck(String loginPw, String replaceUri) {
        if (rq.getLoginedMemberNow().getLoginPw().equals(loginPw) == false) {
            return rq.jsHistoryBack("비밀번호가 일치하지 않습니다.");
        } else {
            return rq.jsReplace("", replaceUri);
        }

    }

    @RequestMapping("/usr/member/modify")
    public String showModify() {
        return "/usr/member/modify";
    }

    @RequestMapping("/usr/member/doModify")
    @ResponseBody
    public String doModify(int id, String password, String name, String nickname,
            String cellphoneNo,
            String email) {
        if (Ut.empty(password)) {
            password = null;
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
        ResultData<Member> modifyRd = memberService.doModify(id, password, name, nickname, cellphoneNo,
                email);

        return Ut.jsReplace(modifyRd.getMsg(), Ut.f("../member/myPage?id=%d", id));
    }
}
