package com.douzone.mysite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.repository.GuestBookDao;
import com.douzone.mysite.service.GuestBookService;
import com.douzone.mysite.vo.GuestBookVo;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	@Autowired
	private GuestBookService guestBookService;

	@RequestMapping("")
	public String list(Model model) {
		List<GuestBookVo> list = guestBookService.getList();
		model.addAttribute("list", list);
		return "/guestbook/list";
	}

	@RequestMapping("/add")
	public String add(GuestBookVo vo) {
		guestBookService.add(vo);
		return "redirect:/guestbook";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete() {
		return "/guestbook/delete";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(GuestBookVo vo) {
		guestBookService.delete(vo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping("/ajax")
	public String ajax(GuestBookVo vo) {
		return "/guestbook/index-ajax";
	}
	
	@RequestMapping("/ajax/list")
	public void listAjax(String p) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data",guestBookService.list(p));
//		response.setContentType("application/json;charset=UTF-8");
//		JSONObject jsonObject = JSONObject.fromObject(map);
//		response.getWriter().print(jsonObject.toString());
	}
	@RequestMapping("/ajax/delete")
	public void deleteAjax(GuestBookVo vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data",guestBookService.remove(vo));
//		response.setContentType("application/json;charset=UTF-8");
//		JSONObject jsonObject = JSONObject.fromObject(map);
//		response.getWriter().print(jsonObject.toString());
	}
	@RequestMapping("/ajax/insert")
	public void insertAjax(GuestBookVo vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data",guestBookService.insert(vo));
//		response.setContentType("application/json;charset=UTF-8");
//		JSONObject jsonObject = JSONObject.fromObject(map);
//		response.getWriter().print(jsonObject.toString());
	}
}
