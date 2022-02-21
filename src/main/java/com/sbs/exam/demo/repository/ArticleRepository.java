package com.sbs.exam.demo.repository;

import java.util.List;

import com.sbs.exam.demo.vo.Article;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleRepository {
	public void writeArticle(@Param("title") String title, @Param("body") String body,
			@Param("memberId") int memberId);

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

	@Select("""
				SELECT A.*,
				M.nickname AS extra__writerName
				FROM article AS A
				LEFT JOIN member AS M
				ON A.memberId = M.id
				ORDER BY A.id DESC
			""")
	public List<Article> getArticles();

	public int getLastInsertId();
}
