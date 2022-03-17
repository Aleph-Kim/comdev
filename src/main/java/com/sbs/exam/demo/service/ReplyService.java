package com.sbs.exam.demo.service;

import java.util.List;

import com.sbs.exam.demo.repository.ReplyRepository;
import com.sbs.exam.demo.vo.Reply;

import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    private ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void addreply(String body, int articleId, int memberId) {
        replyRepository.addreply(body, articleId, memberId);
    }

    public List<Reply> getReplies(int id) {
        return replyRepository.getReplies(id);
    }

    public int getReplyCount(int id) {
        return replyRepository.getReplyCount(id);
    }
}
