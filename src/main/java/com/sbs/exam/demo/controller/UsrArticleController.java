package com.sbs.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sbs.exam.demo.service.ArticleService;
import com.sbs.exam.demo.service.BoardService;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Article;
import com.sbs.exam.demo.vo.Board;
import com.sbs.exam.demo.vo.ResultData;
import com.sbs.exam.demo.vo.Rq;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrArticleController {

	private ArticleService articleService;
	private BoardService boardService;

	public UsrArticleController(ArticleService articleService, BoardService boardService) {
		this.articleService = articleService;
		this.boardService = boardService;
	}

	// 액션 메서드 시작

	@RequestMapping("/usr/article/Add")
	public String Add(HttpServletRequest req) {
		return "/usr/article/create";
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public String doAdd(HttpServletRequest req, String title, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.empty(title)) {
			return Ut.jsHistoryBack("제목을 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return Ut.jsHistoryBack("내용을 입력해주세요.");
		}

		ResultData<Integer> writeArtilceRd = articleService.writeArticle(title, body,
				rq.getLoginedMemberId());

		int id = writeArtilceRd.getData1();

		Article article = articleService.getArticle(id);

		ResultData.newData(writeArtilceRd, article);

		return Ut.jsReplace("게시물이 저장 되었습니다.", Ut.f("../article/detail?id=%d", article.getId()));
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model, int boardId) {
		List<Article> articles = articleService.getArticles();
		Board board = boardService.getBoard(boardId);

		model.addAttribute("articles", articles);
		model.addAttribute("board", board);

		return "/usr/article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id, HttpServletRequest req) {
		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getArticle(id);

		if (article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

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

	@RequestMapping("/usr/article/modify")
	public String showModify(HttpServletRequest req, Model model, int id) {

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

		model.addAttribute("article", article);
		return "/usr/article/modify";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {

		articleService.modifyArticle(id, title, body);

		return Ut.jsReplace("수정되었습니다.", Ut.f("../article/detail?id=%d", id));
	}
}
