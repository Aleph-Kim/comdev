package com.sbs.exam.demo.service;

import java.util.List;

import com.sbs.exam.demo.repository.ArticleRepository;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Article;
import com.sbs.exam.demo.vo.ResultData;

import org.springframework.stereotype.Service;

@Service
public class ArticleService {
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public ResultData<Integer> writeArticle(String title, String body, int memberId) {
		articleRepository.writeArticle(title, body, memberId);
		int id = articleRepository.getLastInsertId();
		return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), "id", id);
	}

	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}

	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}

	public Article getForPrintArticle(int actorId, int id) {
		Article article = articleRepository.getArticle(id);

		if (article.getMemberId() != actorId) {
			return null;
		}

		return article;
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}

	public ResultData<Article> actorCanModify(int actorId, int id) {

		Article article = getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", "게시물이 존재하지 않습니다.");
		}
		if (article.getMemberId() != actorId) {
			return ResultData.from("F-1", "접근 권한이 없습니다.");
		}
		return ResultData.from("S-1", "접근이 가능합니다.");
	}
}
