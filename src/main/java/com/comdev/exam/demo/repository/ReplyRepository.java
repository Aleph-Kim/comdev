package com.comdev.exam.demo.repository;

import java.util.List;

import com.comdev.exam.demo.vo.Reply;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ReplyRepository {

        @Insert("""
                        INSERT reply
                        SET regDate = now(),
                        updateDate = now(),
                        memberId = #{memberId},
                        articleId = #{articleId},
                        `body` = #{body}
                        """)
        void addreply(String body, int articleId, int memberId);

        @Select("""
                        SELECT *,
                        M.nickname AS extra__writerName
                        FROM reply AS R
                        LEFT JOIN `member` AS M
                        ON R.memberId = M.id
                        WHERE articleId = #{id}
                        """)
        List<Reply> getReplies(int id);

        @Select("""
                        SELECT COUNT(*)
                        FROM reply AS R
                        WHERE R.articleId = #{id}
                        """)
        int getReplyCount(int id);

        @Delete("""
                        DELETE FROM reply
                        WHERE id = #{replyId}
                        """)
        void deleteReply(int replyId);

        @Update("""
                        UPDATE reply
                        SET body = #{body}
                        WHERE id = #{replyId}
                        """)
        void modifyReply(int replyId, String body);

        @Select("""
                        SELECT *
                        FROM reply AS R
                        WHERE R.id = #{replyId}
                        """)
        Reply getReply(int replyId);
}
