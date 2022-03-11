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
	private Rq rq;

	public UsrArticleController(ArticleService articleService, BoardService boardService, Rq rq) {
		this.articleService = articleService;
		this.boardService = boardService;
		this.rq = rq;
	}

	// 액션 메서드 시작

	@RequestMapping("/usr/article/Add")
	public String Add(HttpServletRequest req) {
		return "/usr/article/create";
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public String doAdd(String title, String body, int boardId) {

		if (Ut.empty(title)) {
			return rq.jsHistoryBack("제목을 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return rq.jsHistoryBack("내용을 입력해주세요.");
		}

		ResultData<Integer> writeArtilceRd = articleService.writeArticle(title, body,
				rq.getLoginedMemberId(), boardId);

		int id = writeArtilceRd.getData1();

		Article article = articleService.getArticle(id);

		ResultData.newData(writeArtilceRd, article);

		return rq.jsReplace("게시물이 저장 되었습니다.", Ut.f("../article/detail?id=%d", article.getId()));
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model, int boardId) {
		Board board = boardService.getBoard(boardId);
		List<Article> articles;

		if (board == null) {
			return rq.jsHistoryBackOnView("존재하지 않는 게시판입니다.");
		}

		articles = articleService.getArticlesInBoard(boardId);

		model.addAttribute("articles", articles);
		model.addAttribute("board", board);

		return "/usr/article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		model.addAttribute("article", article);

		return "/usr/article/detail";
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		if (Ut.empty(id)) {
			return Ut.jsHistoryBack("게시물 번호를 입력해주세요.");
		}

		ResultData<Article> actorCanModify = articleService.actorCanModify(rq.getLoginedMemberId(), id);
		if (actorCanModify.isFail()) {
			String msg = actorCanModify.getMsg();
			return rq.jsHistoryBack(msg);
		}
		articleService.deleteArticle(id);

		return rq.jsReplace(Ut.f("%d번 게시물을 삭제 했습니다.", id), "../article/list");
	}

	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int id) {
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
	public String doModify(int id, String title, String body) {
		articleService.modifyArticle(id, title, body);

		return rq.jsReplace("수정되었습니다.", Ut.f("../article/detail?id=%d", id));
	}
}
