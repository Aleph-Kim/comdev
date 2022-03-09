package com.sbs.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.sbs.exam.demo.service.ArticleService;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Article;
import com.sbs.exam.demo.vo.ResultData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;
	private boolean isLogined = false;
	private int memberId;

	// 액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpSession httpSession, String title, String body) {

		if (httpSession.getAttribute("LoginedMemberId") != null) {
			memberId = (int) httpSession.getAttribute("LoginedMemberId");
			isLogined = true;
		}

		if (!isLogined) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}

		if (Ut.empty(title)) {
			return ResultData.from("F-1", "title을(를) 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return ResultData.from("F-2", "body을(를) 입력해주세요.");
		}

		ResultData<Integer> writeArtilceRd = articleService.writeArticle(title, body,
				memberId);

		int id = writeArtilceRd.getData1();

		Article article = articleService.getArticle(id);

		return ResultData.newData(writeArtilceRd, article);
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model) {
		// ResultData.from("S-12", "게시물 목록입니다.", "articles",
		List<Article> articles = articleService.getArticles();

		model.addAttribute("articles", articles);

		return "/usr/article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {
		Article article = articleService.getArticle(id);

		model.addAttribute("article", article);

		return "/usr/article/detail";
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpSession httpSession, int id) {

		if (httpSession.getAttribute("LoginedMemberId") != null) {
			memberId = (int) httpSession.getAttribute("LoginedMemberId");
			isLogined = true;
		}

		if (!isLogined) {
			return Ut.jsHistoryBack("로그인 후 이용해주세요.");
		}

		if (Ut.empty(id)) {
			return Ut.jsHistoryBack("게시물 번호를 입력해주세요.");
		}

		articleService.deleteArticle(id);

		return Ut.jsReplace(Ut.f("%d번 게시물을 삭제 했습니다.", id), "../article/list");
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpSession httpSession, int id, String title, String body) {
		if (httpSession.getAttribute("LoginedMemberId") != null) {
			memberId = (int) httpSession.getAttribute("LoginedMemberId");
			isLogined = true;
		}

		if (!isLogined) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}

		if (Ut.empty(id)) {
			return ResultData.from("F-1", "게시물 번호를 입력해주세요.");
		}

		Article article = articleService.getArticle(id);

		ResultData<Article> actorCanModify = articleService.actorCanModify(memberId, article);
		if (actorCanModify.isFail()) {
			return actorCanModify;
		}

		articleService.modifyArticle(id, title, body);

		article = articleService.getArticle(id);

		return ResultData.from("F-3", Ut.f("%d번 게시물을 수정했습니다.", id), "article", article);
	}
	// 액션 메서드 끝
}
