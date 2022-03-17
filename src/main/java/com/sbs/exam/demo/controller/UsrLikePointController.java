package com.sbs.exam.demo.controller;

import com.sbs.exam.demo.service.LikePointService;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Rq;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrLikePointController {

    private Rq rq;
    private LikePointService likePointService;

    public UsrLikePointController(Rq rq, LikePointService likePointService) {
        this.rq = rq;
        this.likePointService = likePointService;
    }

    @RequestMapping("/usr/article/doIncreaseLikePoint")
    @ResponseBody
    public String doIncreaseLikePoint(int articleId) {

        likePointService.doIncreaseLikePoint(articleId, rq.getLoginedMemberId());
        return rq.jsReplace("", Ut.f("../article/detail?id=%d", articleId));
    }

    @RequestMapping("/usr/article/doDecreaseLikePoint")
    @ResponseBody
    public String doDecreaseLikePoint(int articleId) {

        likePointService.doDecreaseLikePoint(articleId, rq.getLoginedMemberId());
        return rq.jsReplace("", Ut.f("../article/detail?id=%d", articleId));
    }
}
