package com.sbs.exam.demo.controller;

import com.sbs.exam.demo.service.ReplyService;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Rq;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrReplyController {

    private Rq rq;
    private ReplyService replyService;

    public UsrReplyController(Rq rq, ReplyService replyService) {
        this.rq = rq;
        this.replyService = replyService;
    }

    @RequestMapping("/usr/reply/doWrite")
    @ResponseBody
    public String doAdd(String body, int articleId) {

        replyService.addReply(body, articleId, rq.getLoginedMemberId());

        return rq.jsReplace("댓글이 등록되었습니다.", Ut.f("../article/detail?id=%d", articleId));
    }

    @RequestMapping("/usr/reply/doDelete")
    @ResponseBody
    public String doDelete(int replyId, int articleId) {

        replyService.doDelete(replyId);

        return rq.jsReplace("댓글이 삭제되었습니다.", Ut.f("../article/detail?id=%d", articleId));
    }
}