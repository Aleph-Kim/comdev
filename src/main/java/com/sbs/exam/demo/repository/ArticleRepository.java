package com.sbs.exam.demo.repository;

import java.util.List;

import com.sbs.exam.demo.vo.Article;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleRepository {
	public void writeArticle(@Param("title") String title, @Param("body") String body,
			@Param("memberId") int memberId);

	public Article getArticle(@Param("id") int id);

	public void deleteArticle(@Param("id") int id);

	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	public List<Article> getArticles();

	public int getLastInsertId();
}
