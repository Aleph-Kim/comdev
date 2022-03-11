package com.sbs.exam.demo.repository;

import java.util.List;

import com.sbs.exam.demo.vo.Article;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleRepository {
	public void writeArticle(@Param("title") String title, @Param("body") String body,
			@Param("memberId") int memberId, @Param("boardId") int boardId);

	@Select("""
				select A.*,
				M.nickname AS extra__writerName
				from article AS A
				LEFT JOIN member AS M
				ON A.memberId = M.id
				where A.id = #{id}
			""")
	public Article getArticle(@Param("id") int id);

	public void deleteArticle(@Param("id") int id);

	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	public int getLastInsertId();

	@Select("""
				<script>
					SELECT A.*,
					M.nickname AS extra__writerName
					FROM article AS A
					LEFT JOIN member AS M
					ON A.memberId = M.id
					WHERE boardId = #{boardId}
					ORDER BY A.id DESC
					<if test="limitTake > -1">
						LIMIT #{limitStart}, #{limitTake}
					</if>
				</script>
			""")
	public List<Article> getArticles(@Param("boardId") int boardId, int limitStart,
			int limitTake);

	@Select("""
			SELECT COUNT(*)
			FROM article AS A
			WHERE A.boardId = #{boardId}
			""")
	public int getArticlesCount(@Param("boardId") int boardId);
}
