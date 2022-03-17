package com.sbs.exam.demo.repository;

import java.util.List;

import com.sbs.exam.demo.vo.Reply;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReplyRepository {

        @Insert("""
                        insert reply
                        set regDate = now(),
                        updateDate = now(),
                        memberId = #{memberId},
                        articleId = #{articleId},
                        `body` = #{body}
                        """)
        void addreply(String body, int articleId, int memberId);

        @Select("""
                        select *,
                        M.nickname AS extra__writerName
                        from reply AS R
                        LEFT JOIN `member` AS M
                        ON R.memberId = M.id
                        where articleId = #{id}
                        """)
        List<Reply> getReplies(int id);

        @Select("""
                        SELECT COUNT(*)
                        FROM reply AS R
                        WHERE R.articleId = #{id}
                        """)
        int getReplyCount(int id);

        @Delete("""
                        delete From reply
                        where id = #{replyId}
                        """)
        void deleteReply(int replyId);
}
