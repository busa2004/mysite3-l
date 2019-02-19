package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.repository.UserDao;
import com.douzone.mysite.vo.BoardVo;
@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	public Model list(HttpServletRequest request, Model model) {
		List<BoardVo> list =null;
		int page = 0 ;
		int movePage = 0; 
		int move =  0;
		int totalSize;
		String search =null;
		BoardDao dao = new BoardDao();
		if( request.getParameter("kwd") !=null)
			search = request.getParameter("kwd");
		if( request.getParameter("page") !=null)
		 page = Integer.parseInt(request.getParameter("page"))-1;
		if( request.getParameter("movePage") !=null)
		 movePage =  Integer.parseInt(request.getParameter("movePage"));
		if( request.getParameter("move") !=null)
		 move =  Integer.parseInt(request.getParameter("move"));
		
		
		if(search != "" && search != null) {
			 list = dao.getList(page*10,10,search);
			 totalSize =dao.getList(search).size();
		}else {
			list = dao.getList(page*10,10);
			totalSize =dao.getList().size();
		}
		
		int totalPage = (int)(totalSize/10);
		if(totalSize%10 != 0) {
			totalPage+=1;
		}
		
		System.out.println("==========================" + movePage);
		if(move == -1 && movePage+1 <= 0 ) {
			System.out.println("==1");
			move=0;
		}
		
		// || move == 1 && movePage+5 > totalPage
		System.out.println(list.size());
		movePage+=move;
		if(movePage+1 <= 0) {
			System.out.println("==2");
			movePage = 0;
		}
		else if(movePage+5 > totalPage) {
			System.out.println("==3");
			movePage-=move;
		}
		// 데이터를 request 범위에 저장
//		for(int i = 0 ; i < list.size();i++) {
//			System.out.println("----------");
//			System.out.println(list);
//			System.out.println("----------");
//		}
		model.addAttribute("list", list);
		model.addAttribute("listSize", list.size());
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalSize", totalSize);
		model.addAttribute("movePage",movePage) ;
		model.addAttribute("kwd",search) ;
		System.out.println(page+" "+totalPage+" "+ movePage+" " + move);
		return model;
		
	}
}
