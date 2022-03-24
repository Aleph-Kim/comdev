package com.comdev.exam.demo.controller;

import com.comdev.exam.demo.service.ReplyService;
import com.comdev.exam.demo.util.Ut;
import com.comdev.exam.demo.vo.Reply;
import com.comdev.exam.demo.vo.ResultData;
import com.comdev.exam.demo.vo.Rq;

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

    @RequestMapping("/usr/reply/doModify")
    @ResponseBody
    public String doModify(int replyId, int articleId, String body) {

        ResultData<Reply> actorCanModify = replyService.actorCanModify(rq.getLoginedMemberId(), replyId);
        if (actorCanModify.isFail()) {
            String msg = actorCanModify.getMsg();
            return rq.jsReplace(msg, Ut.f("../article/detail?id=%d", articleId));
        }

        replyService.doModify(replyId, body);

        return rq.jsReplace("댓글이 수정되었습니다.", Ut.f("../article/detail?id=%d", articleId));
    }
}