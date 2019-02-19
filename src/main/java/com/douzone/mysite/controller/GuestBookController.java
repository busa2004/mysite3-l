package com.douzone.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.repository.GuestBookDao;
import com.douzone.mysite.repository.UserDao;
import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	@RequestMapping("")
	public String list(HttpServletRequest request) {
		GuestBookDao dao = new GuestBookDao();
		List<GuestBookVo> list = dao.getList();

		// 데이터를 request 범위에 저장
		request.setAttribute("list", list);
		// forwarding
		return "/guestbook/list";
		
	}
	@RequestMapping("/add")
	public String add(HttpServletRequest request,GuestBookVo vo) {
		new GuestBookDao().insert(vo);
		
		return "redirect:/guestbook";
		
	}
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete() {
		return "/guestbook/delete";
	}
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String delete(HttpServletRequest request,GuestBookVo vo) {

		new GuestBookDao().delete(vo);
		//반환으로 문구 결정
		return "redirect:/guestbook";
	}
}
