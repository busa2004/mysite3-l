package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {

	@Autowired
	private SqlSession sqlSession;

	public GuestBookVo get() {
		return sqlSession.selectOne("guestbook.get");
	}

	public boolean select(GuestBookVo vo) {
		long no = -1;
		no = sqlSession.selectOne("guestbook.select", vo);
		if (no == -1) {
			return false;
		}
		return true;
	}

	public Long getId() {
		return sqlSession.selectOne("guestbook.getId");
	}

	public long insert(GuestBookVo vo) {
		sqlSession.insert("guestbook.insert", vo);
		long no = vo.getNo();
		return no;
	}

	public List<GuestBookVo> getList() {
		List<GuestBookVo> list = sqlSession.selectList("guestbook.getList");
		return list;
	}

	public List<GuestBookVo> getList(int page) {
		List<GuestBookVo> list = sqlSession.selectList("guestbook.getListByPage", page);
		return list;
	}

	public void delete(GuestBookVo vo) {
		sqlSession.delete("guestbook.delete", vo);
	}

}
