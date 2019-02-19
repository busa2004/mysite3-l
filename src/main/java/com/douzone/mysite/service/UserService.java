package com.douzone.mysite.service;

import javax.servlet.http.HttpServletRequest;

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
		//1. DB에 가입 회원 정보 insert 하기
			userDao.insert(userVo);
		//2. email 주소 확인하는 메일 보내기
	}

	
}
