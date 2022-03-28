package com.comdev.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.comdev.exam.demo.service.ArticleService;
import com.comdev.exam.demo.service.BoardService;
import com.comdev.exam.demo.service.LikePointService;
import com.comdev.exam.demo.service.ReplyService;
import com.comdev.exam.demo.util.Ut;
import com.comdev.exam.demo.vo.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrArticleController {

	private ArticleService articleService;
	private BoardService boardService;
	private ReplyService replyService;
	private LikePointService likePointService;
	private Rq rq;

	public UsrArticleController(ArticleService articleService, BoardService boardService, ReplyService replyService, LikePointService likePointService,
			Rq rq) {
		this.articleService = articleService;
		this.boardService = boardService;
		this.replyService = replyService;
		this.likePointService = likePointService;
		this.rq = rq;
	}

	// 액션 메서드 시작

	@RequestMapping("/usr/article/Add")
	public String Add(HttpServletRequest req, Model model) {
		return "/usr/article/create";
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public String doAdd(String title, String body, int boardId) {

		if (Ut.empty(boardId)) {
			return rq.jsHistoryBack("게시판을 선택해주세요.");
		}

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
	public String showList(Model model,
			@RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "") String searchKeyword,
			@RequestParam(defaultValue = "title, body") String searchKeywordType,
			@RequestParam(defaultValue = "1") int page) {
		Board board = boardService.getBoard(boardId);
		List<Article> articles;

		if (board == null) {
			return rq.jsHistoryBackOnView("존재하지 않는 게시판입니다.");
		}

		int itemsCountInPage = 10;

		articles = articleService.getArticles(boardId, itemsCountInPage, searchKeyword, searchKeywordType, page);

		if (articles.size() == 0) {
			return rq.jsHistoryBackOnView("존재하지 않는 페이지입니다.");
		}

		int articlesCount = articleService.getArticlesCount(board, searchKeyword, searchKeywordType);
		int pageCount = (int) Math.ceil((double) articlesCount / 10.0);

		model.addAttribute("page", page);
		model.addAttribute("articles", articles);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("board", board);

		return "/usr/article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		Board board = boardService.getBoard(article.getBoardId());
		List<Reply> replies = replyService.getReplies(id);
		int replyCount = replyService.getReplyCount(id);

		model.addAttribute("article", article);
		model.addAttribute("board", board);
		model.addAttribute("replies", replies);
		model.addAttribute("replyCount", replyCount);

		boolean actorCanMakeLikePoint = likePointService.actorCanMakeLikePoint(rq.getLoginedMemberId(), id);

		model.addAttribute("actorCanMakeLikePoint", actorCanMakeLikePoint);

		return "/usr/article/detail";
	}

	@RequestMapping("/usr/article/doIncreaseHitCount")
	@ResponseBody
	public String doincreaseHitcount(int id) {
		articleService.increaseHitCount(id);
		return "증가완료";
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id, int boardId) {
		if (Ut.empty(id)) {
			return Ut.jsHistoryBack("게시물 번호를 입력해주세요.");
		}

		ResultData<Article> actorCanModify = articleService.actorCanModify(rq.getLoginedMemberId(), id);
		if (actorCanModify.isFail()) {
			String msg = actorCanModify.getMsg();
			return rq.jsHistoryBack(msg);
		}
		articleService.deleteArticle(id);

		return rq.jsReplace(Ut.f("%d번 게시물을 삭제 했습니다.", id), Ut.f("../article/list?boardId=%d", boardId));
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
