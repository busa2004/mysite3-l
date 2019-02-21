package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.CommentVo;

@Repository
public class CommentDao {

	@Autowired
	private SqlSession sqlSession;

	public List<CommentVo> getList(long no) {
		List<CommentVo> list = sqlSession.selectList("comment.getList", no);
		return list;
	}

	public void insert(CommentVo vo) {
		sqlSession.insert("comment.insert", vo);
	}

	public void delete(Long no) {
		sqlSession.delete("comment.delete", no);
	}

	public void deleteAll(long no) {
		sqlSession.delete("comment.deleteAll", no);
	}

}
