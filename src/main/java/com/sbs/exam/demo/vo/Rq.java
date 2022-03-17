package com.sbs.exam.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.exam.demo.service.MemberService;
import com.sbs.exam.demo.util.Ut;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {

    @Getter
    private boolean isLogined;

    @Getter
    private int loginedMemberId;

    @Getter
    private Member loginedMemberNow;

    private HttpServletRequest req;
    private HttpServletResponse resp;
    private HttpSession httpSession;

    public Rq(HttpServletRequest req, HttpServletResponse resp, Rq rq, MemberService memberService) {
        this.req = req;
        this.resp = resp;
        this.httpSession = this.req.getSession();
        this.req.setAttribute("rq", this);

        boolean isLogined = false;
        int loginedMemberId = 0;
        Member loginedMemberNow = null;

        if (httpSession.getAttribute("LoginedMemberId") != null) {
            isLogined = true;
            loginedMemberId = (int) httpSession.getAttribute("LoginedMemberId");
            loginedMemberNow = (Member) memberService.searchUserId(loginedMemberId);
        }

        this.isLogined = isLogined;
        this.loginedMemberId = loginedMemberId;
        this.loginedMemberNow = loginedMemberNow;

        this.req.setAttribute("rq", this);
    }

    public void printHistoryBackJs(String msg) {
        resp.setContentType("text/html; charset=UTF-8");

        print(Ut.jsHistoryBack(msg));

    }

    private void print(String str) {
        try {
            resp.getWriter().append(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(Member member) {
        httpSession.setAttribute("LoginedMemberId", member.getId());
        httpSession.setAttribute("LoginedMember", member);
    }

    public Member getLoginedMember() {
        return (Member) httpSession.getAttribute("LoginedMemberId");
    }

    public void logout() {
        httpSession.removeAttribute("LoginedMemberId");
        httpSession.removeAttribute("LoginedMember");
    }

    public String jsHistoryBackOnView(String msg) {
        req.setAttribute("msg", msg);
        req.setAttribute("historyBack", true);
        return "common/js";
    }

    public String jsHistoryBack(String msg) {
        return Ut.jsHistoryBack(msg);
    }

    public String jsReplace(String msg, String uri) {
        return Ut.jsReplace(msg, uri);
    }

    // Rq 객체가 자연스럽게 생성되도록 유도하는 역할
    public void initOnBeforeActionInterceptor() {
    }

}
