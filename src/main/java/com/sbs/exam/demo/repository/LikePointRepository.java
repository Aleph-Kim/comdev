package com.sbs.exam.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikePointRepository {

        @Insert("""
                        insert into likePoint
                        set regDate = now(),
                        updateDate = now(),
                        memberId = #{memberId},
                        relId = #{articleId}
                        """)
        public void doIncreaseLikePoint(int articleId, int memberId);

        @Delete("""
                        delete
                        from likePoint
                        where memberId = #{memberId}
                        AND relId = #{articleId}
                        """)
        public void doDecreaseLikePoint(int articleId, int memberId);

}
