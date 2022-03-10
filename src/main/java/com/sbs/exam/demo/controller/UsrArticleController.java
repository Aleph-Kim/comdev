package com.sbs.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sbs.exam.demo.service.ArticleService;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Article;
import com.sbs.exam.demo.vo.ResultData;
import com.sbs.exam.demo.vo.Rq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;

	// 액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpServletRequest req, String title, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.empty(title)) {
			return ResultData.from("F-1", "title을(를) 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return ResultData.from("F-2", "body을(를) 입력해주세요.");
		}

		ResultData<Integer> writeArtilceRd = articleService.writeArticle(title, body,
				rq.getLoginedMemberId());

		int id = writeArtilceRd.getData1();

		Article article = articleService.getArticle(id);

		return ResultData.newData(writeArtilceRd, article);
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model) {
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
	public String doDelete(HttpServletRequest req, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.empty(id)) {
			return Ut.jsHistoryBack("게시물 번호를 입력해주세요.");
		}

		ResultData<Article> actorCanModify = articleService.actorCanModify(rq.getLoginedMemberId(), id);
		if (actorCanModify.isFail()) {
			String msg = actorCanModify.getMsg();
			return Ut.jsHistoryBack(msg);
		}
		articleService.deleteArticle(id);

		return Ut.jsReplace(Ut.f("%d번 게시물을 삭제 했습니다.", id), "../article/list");
	}

	@RequestMapping("/usr/article/doModify")
	public String modify(HttpServletRequest req, int id, String title, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.empty(id)) {
			return Ut.jsHistoryBack("게시물 번호를 입력해주세요.");
		}

		Article article = articleService.getArticle(id);

		if (article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		ResultData<Article> actorCanModify = articleService.actorCanModify(rq.getLoginedMemberId(), id);
		if (actorCanModify.isFail()) {
			String msg = actorCanModify.getMsg();
			return rq.jsHistoryBackOnView(msg);
		}

		return "/usr/article/modify";
	}
}
