package com.douzone.mysite.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.repository.UserDao;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "/user/join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute UserVo userVo) {
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "/user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, String email, String password) {
		UserVo vo = userService.login(email, password);
		if (vo == null) {
			// 인증 실패
			request.setAttribute("result", "fail");
			return "/user/login";
		}
		// 인증성공 ->
		HttpSession session = request.getSession(true);
		session.setAttribute("authuser", vo);
		return "redirect:/main";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session != null && session.getAttribute("authuser") != null) {
			session.removeAttribute("authuser");
			session.invalidate();
		}
		return "redirect:/main";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(HttpServletRequest request) {
		if (userService.auth(request) == null)
			return "redirect:/main";
		UserVo vo = userService.modify(userService.auth(request).getNo());
		request.setAttribute("vo", vo);
		return "user/modify";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(HttpServletRequest request,UserVo vo) {
		UserVo authUser = null;
		HttpSession session = request.getSession();
		if (session != null) {
			authUser = (UserVo) session.getAttribute("authuser");
		}
		if (authUser == null) {
			return "redirect:/main";
		}
		vo.setNo(authUser.getNo());
			userService.modify(vo);
		authUser.setName(vo.getName());
		session.setAttribute("authuser", authUser);
		return "redirect:/main";
	}
}
