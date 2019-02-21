package com.douzone.mysite.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String list( Model model,BoardVo vo) {
		boardService.list( model, vo);
		return "/board/list";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(HttpServletRequest request) {
		if (boardService.auth(request) == null)
			return "/main";
		return "/board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(HttpServletRequest request, Model model) {
		if (boardService.auth(request) == null)
			return "/main";
		boardService.write(request, model, boardService.auth(request));
		return "redirect:/board";
	}

	@RequestMapping("/view")
	public String view(HttpServletRequest request, HttpServletResponse response, Model model,BoardVo vo) {
		return boardService.view(request, response, model,vo);
	}

	@RequestMapping("/remove")
	public String remove(HttpServletRequest request, Long no) {
		if (boardService.auth(request) == null)
			return "/main";
		return boardService.remove(request, no);
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(HttpServletRequest request, Long no, Model model) {
		if (boardService.auth(request) == null)
			return "/main";
		BoardVo vo = boardService.modify(no);
		if (boardService.auth(request).getNo() != vo.getUserNo()) {
			return "redirect:/board";
		}
		model.addAttribute("vo", vo);
		return "/board/modify";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(HttpServletRequest request, BoardVo vo) {
		if (boardService.auth(request) == null)
			return "redirect:/board";
		boardService.modify(vo);
		return "redirect:/board";
	}

	@RequestMapping("/comment")
	public String comment(HttpServletRequest request,CommentVo vo) {
		if (boardService.auth(request) == null)
			return "/main";
		boardService.comment(vo);
		return "redirect:/board/view?no=" + vo.getBoardNo();
	}

	@RequestMapping("/commentremove")
	public String commentremove(HttpServletRequest request, Long boardNo, Long no) {
		if (boardService.auth(request) == null)
			return "/main";
		boardService.commentremove(no);
		return "redirect:/board/view?no=" + boardNo;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String reply(HttpServletRequest request, String no, Model model) {
		model.addAttribute("no", no);
		return "/board/write";
	}

	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(HttpServletRequest request, Long no, String title, String contents) {
		if (boardService.auth(request) == null)
			return "/main";
		else {
			boardService.reply(no, title, contents, boardService.auth(request));
		}
		return "redirect:/board";
	}

	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
		boardService.download(request, response, fileName);
	}
}
