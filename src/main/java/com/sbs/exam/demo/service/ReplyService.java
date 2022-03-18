package com.sbs.exam.demo.service;

import java.util.List;

import com.sbs.exam.demo.repository.ReplyRepository;
import com.sbs.exam.demo.vo.Reply;
import com.sbs.exam.demo.vo.ResultData;

import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    private ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void addReply(String body, int articleId, int memberId) {
        replyRepository.addreply(body, articleId, memberId);
    }

    public List<Reply> getReplies(int id) {
        return replyRepository.getReplies(id);
    }

    public int getReplyCount(int id) {
        return replyRepository.getReplyCount(id);
    }

    public void doDelete(int replyId) {
        replyRepository.deleteReply(replyId);
    }

    public void doModify(int replyId, String body) {
        replyRepository.modifyReply(replyId, body);
    }

    public ResultData<Reply> actorCanModify(int actorId, int replyId) {
        Reply reply = getReply(replyId);

        if (reply == null) {
            return ResultData.from("F-1", "댓글이 존재하지 않습니다.");
        }
        if (reply.getMemberId() != actorId) {
            return ResultData.from("F-1", "접근 권한이 없습니다.");
        }
        return ResultData.from("S-1", "접근이 가능합니다.");
    }

    private Reply getReply(int replyId) {
        return replyRepository.getReply(replyId);
    }

}
