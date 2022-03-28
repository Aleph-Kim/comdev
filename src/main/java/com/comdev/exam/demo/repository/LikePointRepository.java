package com.comdev.exam.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LikePointRepository {

        @Insert("""
                        INSERT INTO likePoint
                        SET regDate = now(),
                        updateDate = now(),
                        memberId = #{memberId},
                        relId = #{articleId}
                        """)
        public void doIncreaseLikePoint(int articleId, int memberId);

        @Delete("""
                        DELETE
                        FROM likePoint
                        WHERE memberId = #{memberId}
                        AND relId = #{articleId}
                        """)
        public void doDecreaseLikePoint(int articleId, int memberId);

        @Select("""
			<script>
			SELECT IFNULL(SUM(LP.like), 0) AS s
			FROM likePoint AS LP
			WHERE LP.relId = #{id}
			AND LP.memberId = #{memberId}
			</script>
			""")
	public int getLikePointByMemberId(int id, int memberId);
}
