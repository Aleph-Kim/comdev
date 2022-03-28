package com.comdev.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comdev.exam.demo.vo.Rq;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BeforeActionInterceptor implements HandlerInterceptor {

    private Rq rq;

    public BeforeActionInterceptor(Rq rq) {
        this.rq = rq;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        req.setAttribute("rq", rq);

        return HandlerInterceptor.super.preHandle(req, resp, handler);
    }
}