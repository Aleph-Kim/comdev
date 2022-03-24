package com.comdev.exam.demo.repository;

import java.util.List;

import com.comdev.exam.demo.vo.Article;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ArticleRepository {
	public void writeArticle(@Param("title") String title, @Param("body") String body,
			@Param("memberId") int memberId, @Param("boardId") int boardId);

	@Select("""
				<script>
					SELECT A.*,
					IFNULL(SUM(LP.like), 0) AS extra__LikePoint
					FROM (
						select A.*,
						M.nickname AS extra__writerName
						from article AS A
						LEFT JOIN `member` AS M
						ON A.memberId = M.id
						where A.id = #{id}
					) AS A
					LEFT JOIN likePoint AS LP
					ON A.id = LP.relId
					GROUP BY A.id
				</script>
			""")
	public Article getArticle(@Param("id") int id);

	public void deleteArticle(@Param("id") int id);

	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	public int getLastInsertId();

	@Select("""
				<script>
					SELECT A.*,
					IFNULL(SUM(LP.like), 0) AS extra__LikePoint
					FROM (
						SELECT A.*,
						M.nickname AS extra__writerName
						FROM article AS A
						LEFT JOIN `member` AS M
						ON A.memberId = M.id
						WHERE 1
						AND boardId = #{boardId}
						<if test="searchKeyword != ''">
							<choose>
								<when test="searchKeywordType == 'title'">
									AND A.title LIKE CONCAT('%', #{searchKeyword}, '%')
								</when>
								<when test="searchKeywordType == 'body'">
									AND A.body LIKE CONCAT('%', #{searchKeyword}, '%')
								</when>
								<otherwise>
									AND (
										A.title LIKE CONCAT('%', #{searchKeyword}, '%')
										OR
										A.body LIKE CONCAT('%', #{searchKeyword}, '%')
									)
								</otherwise>
							</choose>
						</if>
						<if test="limitTake > -1">
							LIMIT #{limitStart}, #{limitTake}
						</if>
					) AS A
					LEFT JOIN likePoint AS LP
					ON A.id = LP.relId
					GROUP BY A.id
					ORDER BY A.id DESC
				</script>
			""")
	public List<Article> getArticles(@Param("boardId") int boardId, int limitStart,
			int limitTake, String searchKeyword, String searchKeywordType);

	@Select("""
			<script>
			SELECT COUNT(*)
			FROM article AS A
			WHERE A.boardId = #{boardId}
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordType == 'title'">
						AND A.title LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordType == 'body'">
						AND A.body LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<otherwise>
						AND (
							A.title LIKE CONCAT('%', #{searchKeyword}, '%')
							OR
							A.body LIKE CONCAT('%', #{searchKeyword}, '%')
						)
					</otherwise>
				</choose>
			</if>
			</script>
			""")
	public int getArticlesCount(@Param("boardId") int boardId, String searchKeyword, String searchKeywordType);

	@Update("""
			update article
			set hitCount = hitCount + 1
			where id = #{id}
			""")
	public int increaseHitCount(int id);

	@Select("""
			<script>
			SELECT IFNULL(SUM(LP.like), 0) AS s
			FROM likePoint AS LP
			WHERE Lp.relId = #{id}
			AND Lp.memberId = #{memberId}
			</script>
			""")
	public int getLikePointByMemberId(int id, int memberId);
}
