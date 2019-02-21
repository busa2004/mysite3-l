package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	@Autowired
	private GuestBookDao GuestBookDao;

	public List<GuestBookVo> getList() {
		return GuestBookDao.getList();
	}

	public void add(GuestBookVo vo) {
		GuestBookDao.insert(vo);

	}

	public void delete(GuestBookVo vo) {
		GuestBookDao.delete(vo);

	}

	public List<GuestBookVo> list(String p) {
		String sPage = p;
		if("".equals(sPage)) {
			sPage="1";
		}
		if(sPage.matches("\\d*")==false) {
			sPage="1";
		}
		int page = Integer.parseInt(sPage);
		return GuestBookDao.getList(page);
	}

	public GuestBookVo insert(GuestBookVo vo) {
		GuestBookDao.insert(vo);
		return GuestBookDao.get();
	}

	public boolean remove(GuestBookVo vo) {
		boolean passwordCheck = GuestBookDao.select(vo);
		if(passwordCheck) {
			GuestBookDao.delete(vo);
		}
		return passwordCheck;
	}



}
