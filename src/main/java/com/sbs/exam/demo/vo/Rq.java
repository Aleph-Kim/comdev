package com.sbs.exam.demo.vo;

import java.io.IOException;
import java.util.Map;

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
    private Map<String, String> paramMap;

    public Rq(HttpServletRequest req, HttpServletResponse resp, Rq rq, MemberService memberService) {
        this.req = req;
        this.resp = resp;

        paramMap = Ut.getParamMap(req);

        this.httpSession = this.req.getSession();

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

    }

    public void printReplaceJs(String msg, String uri) {
        resp.setContentType("text/html; charset=UTF-8");
        print(Ut.jsReplace(msg, uri));
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

    public String getCurrentUri() {
        String currentUri = req.getRequestURI();
        String queryString = req.getQueryString();

        if (queryString != null && queryString.length() > 0) {
            currentUri += "?" + queryString;
        }

        return currentUri;
    }

    public String getEncodedCurrentUri() {
        return Ut.getUriEncoded(getCurrentUri());
    }

    public String getAfterLoginUri() {
        String requestUri = req.getRequestURI();

        switch (requestUri) {
            case "/usr/member/login":
            case "/usr/member/join":
            case "/usr/member/findLoginId":
            case "/usr/member/findLoginPw":
                return Ut.getUriEncoded(Ut.getStrAttr(paramMap, "afterLoginUri", ""));
        }

        return getEncodedCurrentUri();
    }

    public String getAfterLogoutUri() {
        return getEncodedCurrentUri();
    }

    public String getLoginUri() {
        return "../member/login?afterLoginUri=" + getAfterLoginUri();
    }

    public String getLogoutUri() {
        String requestUri = req.getRequestURI();

        switch (requestUri) {
            case "/usr/article/Add":
                return "../member/doLogout";
            case "/usr/article/doAdd":
                return "../member/doLogout";
        }

        return "../member/doLogout?afterLogoutUri=" + getAfterLogoutUri();
    }
}
