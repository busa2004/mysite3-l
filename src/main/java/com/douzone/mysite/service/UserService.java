package com.douzone.mysite.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.douzone.mysite.repository.UserDao;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public void join(UserVo userVo) {
		// 1. DB에 가입 회원 정보 insert 하기
		userDao.insert(userVo);
		// 2. email 주소 확인하는 메일 보내기
	}

	public UserVo login(String email, String password) {
		return userDao.get(email, password);
	}

	public UserVo modify(long no) {
		return userDao.get(no);
	}

	public void modify(UserVo vo) {
		userDao.update(vo);
	}

	public UserVo auth(HttpServletRequest request) {
		UserVo authUser = null;
		HttpSession session = request.getSession();
		if (session != null) {
			authUser = (UserVo) session.getAttribute("authuser");
		}
		return authUser;
	}

}
