package com.comdev.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comdev.exam.demo.vo.Rq;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class NeedLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        Rq rq = (Rq) req.getAttribute("rq");

        if (!rq.isLogined()) {

            if (rq.isAjax()) {
                resp.setContentType("application/json; charset=UTF-8");
                resp.getWriter().append("{\"resultCode\":\"F-A\",\"msg\":\"로그인 후 이용해주세요.\"}");
            } else {
                String afterLoginUri = rq.getEncodedCurrentUri();
                rq.printReplaceJs("로그인 후 이용해주세요.", "../member/login?afterLoginUri=" + afterLoginUri);
            }
            return false;
        }

        return HandlerInterceptor.super.preHandle(req, resp, handler);
    }
}
