package com.sbs.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.exam.demo.vo.Rq;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class NeedLogoutInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        Rq rq = (Rq) req.getAttribute("rq");

        if (rq.isLogined()) {
            rq.printHistoryBackJs("로그아웃 후 이용해주세요.");
            return false;
        }

        return HandlerInterceptor.super.preHandle(req, resp, handler);
    }
}
