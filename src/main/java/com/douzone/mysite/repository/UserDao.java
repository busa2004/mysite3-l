package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;

	public int insert(UserVo vo) {
		return sqlSession.insert("user.insert", vo);
	}

	public UserVo get(String email, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		return sqlSession.selectOne("user.getByemailAndPassword", map);
	}

	public UserVo get(String email) {
		return sqlSession.selectOne("user.getByEmail", email);
	}

	public UserVo get(long no) {
		return sqlSession.selectOne("user.getByLong", no);
	}

	public void update(UserVo vo) {
		sqlSession.update("user.update", vo);
	}
}